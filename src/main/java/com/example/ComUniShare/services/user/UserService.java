package com.example.ComUniShare.services.user;

import com.example.ComUniShare.domain.product.Product;
import com.example.ComUniShare.domain.user.User;
import com.example.ComUniShare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IuserService{
    @Autowired
    private UserRepository userRepository; // Você precisará criar este repositório

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User myData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserLogin = authentication.getName();
        return findUserByLogin(currentUserLogin);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> updateUserAdapted(String userId, Map<String, Object> updates) {
        User existingUser = findById(userId);

        if (existingUser == null) {
            return new ResponseEntity<>("Usuário não encontrado com ID: " + userId, HttpStatus.NOT_FOUND);
        }

        // Aplica as atualizações nos campos existentes
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            // Usa reflexão para obter o método setter do campo
            try {
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method[] methods = User.class.getMethods();

                // Encontra o método setter correspondente ao campo
                Method method = Arrays.stream(methods)
                        .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == 1)
                        .findFirst()
                        .orElseThrow(NoSuchMethodException::new);

                // Converte o valor para o tipo esperado pelo método setter
                Object convertedValue = convertValue(value, method.getParameterTypes()[0]);

                // Chama o método setter com o valor adequado
                method.invoke(existingUser, convertedValue);

                System.out.println("method:" + method + " methodname:" + methodName);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return ResponseEntity.badRequest().body("Falha ao atualizar o campo: " + fieldName + " com o valor " + value + " \n " + e);
            }
        }

        // Persiste as alterações
        saveUser(existingUser);

        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    private Object convertValue(Object value, Class<?> targetType) {

        if (targetType.equals(Integer.class) && value instanceof String) {
            return Integer.parseInt((String) value);
        } else {
            return value;
        }
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
