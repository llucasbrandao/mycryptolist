package com.lucasbrandao.mycryptolist.services;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.models.UserModel;
import com.lucasbrandao.mycryptolist.repositories.UsersRepository;
import com.lucasbrandao.mycryptolist.utils.PageableUtils;

@Service
public class TestService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	public void init() {
		/*UserModel user = new UserModel();
		
		user.setEmail("llucasteste@gmail.com");
		user.setName("Lucas");
		user.setId(UUID.randomUUID());
		
		usersRepository.save(user);
		
		Page<?> page = usersRepository.findAll(PageRequest.of(1, 2));
		
		System.out.println("\n\n\t" + PageableUtils.generatePageDTO(page.getContent(), page.getTotalElements(), 
				page.getTotalPages(), page.getNumber(), page.getSize()));*/
		
		/*usersRepository.findByName("Lucas").ifPresentOrElse(users -> users.forEach(e -> System.out.println(e)), () -> {
			throw new RuntimeException("Nenhum usuário foi encontrado.");
		});*/
	}
}
