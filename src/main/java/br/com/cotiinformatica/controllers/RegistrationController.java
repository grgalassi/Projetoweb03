package br.com.cotiinformatica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.dtos.PostRegistrationDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.helpers.MD5Helper;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class RegistrationController {

	@RequestMapping(value = "/api/registration", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody PostRegistrationDTO dto) {

		try {
			if (!dto.getSenha().equals(dto.getSenhaConfirmacao())) {
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Erro: Senhas não conferem.");
			}
			
			UsuarioRepository usuarioRepository = new UsuarioRepository();
			if(usuarioRepository.findByEmail(dto.getEmail()) != null) {
				
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Erro: O email informado ja está cadastrado, tente outro.");
			}
			
			Usuario usuario = new Usuario();
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(MD5Helper.encrypt(dto.getSenha()));
			
			usuarioRepository.create(usuario);
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body("Usuário cadastrado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

}
