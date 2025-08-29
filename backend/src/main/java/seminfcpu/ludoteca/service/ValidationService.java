package seminfcpu.ludoteca.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seminfcpu.ludoteca.entity.User;
import seminfcpu.ludoteca.entity.ValidationEmail;
import seminfcpu.ludoteca.persistence.ValidationRepository;

@Service
public class ValidationService {
    @Autowired
    private ValidationRepository repository;

    public ValidationEmail update(@NotNull ValidationEmail user) {
        return repository.save(user);
    }
    public ValidationEmail getValidation( String Email){
        return repository.findByEmail(Email);
    }
}
