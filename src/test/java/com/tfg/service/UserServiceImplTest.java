//package com.tfg.service;
//
//import com.tfg.dto.UserDto;
//import com.tfg.model.User;
//import com.tfg.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Test
//    void registrarUsuario_correcto() {
//        User user = new User();
//        user.setEmail("test@test.com");
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserDto resultado = userService.createUser(user);
//
//        assertNotNull(resultado);
//        assertEquals("test@test.com", resultado.getEmail());
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    void buscarUsuarioPorEmail_existente() {
//        User user = new User();
//        user.setEmail("test@test.com");
//
//        when(userRepository.findByEmail("test@test.com"))
//                .thenReturn(Optional.of(user));
//
//        User resultado = userService.getUserEntityByEmail("test@test.com");
//
//        assertNotNull(resultado);
//    }
//}