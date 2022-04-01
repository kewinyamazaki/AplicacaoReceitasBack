package com.example.sitereceitas.classes.auth.service;

import com.example.sitereceitas.classes.auth.model.dto.AuthenticationResponse;
import com.example.sitereceitas.classes.auth.model.dto.LoginRequest;
import com.example.sitereceitas.classes.auth.model.dto.RefreshTokenRequest;
import com.example.sitereceitas.classes.auth.security.JwtProvider;
import com.example.sitereceitas.classes.token_validacao.model.common.EmailDeNotificacao;
import com.example.sitereceitas.classes.token_validacao.model.common.TokenDeValidacao;
import com.example.sitereceitas.classes.token_validacao.repository.SRTokenDeValidacaoRepository;
import com.example.sitereceitas.classes.token_validacao.service.MailService;
import com.example.sitereceitas.classes.usuario.converter.SRUsuarioConverter;
import com.example.sitereceitas.classes.usuario.model.common.Usuario;
import com.example.sitereceitas.classes.usuario.model.dto.UsuarioDTO;
import com.example.sitereceitas.classes.usuario.repository.SRUsuarioRepository;
import com.example.sitereceitas.exceptions.NegocioException;
import com.example.sitereceitas.util.MensagensUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final SRUsuarioRepository usuarioRepository;
    private final SRUsuarioConverter usuarioConverter;
    private final SRTokenDeValidacaoRepository tokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        // TO-DO: Verificar que o login não é uma requisição nula
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.gerarToken(authenticate);
        return AuthenticationResponse.builder()
                .autenticacaoToken(token)
                .refreshToken(refreshTokenService.gerarRefreshToken().getToken())
                .expiraEm(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
                .email(loginRequest.getEmail())
                .nome(obterUsuarioAutenticado().getNome())
                .build();
    }

    @Transactional
    public UsuarioDTO cadastrar(UsuarioDTO dto){
        verificarEmailExistente(dto.getEmail());
        Usuario usuario = usuarioConverter.DTOToEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setEmailValidado(false);
        usuarioRepository.save(usuario);

        String token = gerarTokenDeVerificacao(usuario);
        mailService.sendMail(new EmailDeNotificacao("Ativação da conta",
                usuario.getEmail(),
                usuario.getNome(), "https://sreceitas.herokuapp.com/v1/verificar/" + token));

        return usuarioConverter.EntityToDTO(usuario);
    }

    @Transactional
    public Usuario obterUsuarioAutenticado() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return usuarioRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new NegocioException(MensagensUtil.MSG_USUARIO_INVALIDO));
    }

    public Boolean autenticado() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public Boolean validarAutenticidadeId(Long id) {
        return autenticado() ? obterUsuarioAutenticado().getUserId().equals(id) : false;
    }

    private String gerarTokenDeVerificacao(Usuario usuario){
        String token = UUID.randomUUID().toString();
        TokenDeValidacao tokenDeValidacao = new TokenDeValidacao();
        tokenDeValidacao.setToken(token);
        tokenDeValidacao.setUsuario(usuario);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        tokenDeValidacao.setValidade(c.getTime());

        tokenRepository.save(tokenDeValidacao);
        return token;
    }

    @Transactional
    public void verificarEmailExistente(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isPresent()){
            throw new NegocioException(MensagensUtil.MSG_EMAIL_EXISTENTE);
        }
    }

    @Transactional
    public void verificarConta(String token) {
        Optional<TokenDeValidacao> tokenDeValidacao = tokenRepository.findByToken(token);
        tokenDeValidacao.orElseThrow(() -> new NegocioException(MensagensUtil.MSG_TOKEN_INVALIDO));
        buscarValidarUsuario(tokenDeValidacao.get());
    }

    @Transactional
    private void buscarValidarUsuario(TokenDeValidacao tokenDeValidacao) {
        Usuario usuario = tokenDeValidacao.getUsuario();
        usuario.setEmailValidado(true);
        tokenRepository.delete(tokenDeValidacao);
        usuarioRepository.save(usuario);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validarRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.gerarTokenComEmail(refreshTokenRequest.getEmail());
        return AuthenticationResponse.builder()
                .autenticacaoToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiraEm(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
                .email(refreshTokenRequest.getEmail())
                .build();
    }
}
