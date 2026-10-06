package com.uc.ms_security.service;

import com.uc.ms_security.dto.session.CreateSessionDTO;
import com.uc.ms_security.dto.session.SessionResponseDTO;
import com.uc.ms_security.dto.session.UpdateSessionDTO;
import com.uc.ms_security.entity.Session;
import com.uc.ms_security.entity.User;
import com.uc.ms_security.exception.ApplicationException;
import com.uc.ms_security.exception.ErrorCase;
import com.uc.ms_security.mapper.SessionMapper;
import com.uc.ms_security.repository.SessionRepository;
import com.uc.ms_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final UserRepository userRepository;

    private final SessionMapper sessionMapper;

    public SessionResponseDTO create(CreateSessionDTO dto) {
        User user = findUser(dto.getUserId());
        if (sessionRepository.existsByToken(dto.getToken())) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "Ya existe una sesión con este token"
            );
        }
        Session session = sessionMapper.toEntity(dto, user);
        Session savedSession = sessionRepository.save(session);
        return sessionMapper.toResponseDTO(savedSession);
    }
    public List<SessionResponseDTO> findAll() {
        List<Session> sessions = sessionRepository.findAll();
        return sessionMapper.toResponseDTOList(sessions);
    }
    private Session findSession(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Sesión no encontrada con id: " + id
                ));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCase.NOT_FOUND,
                        "Usuario no encontrado con id: " + userId
                ));
    }

    public SessionResponseDTO findById(Long id) {
        Session session = findSession(id);
        return sessionMapper.toResponseDTO(session);
    }

    public SessionResponseDTO update(Long id, UpdateSessionDTO dto) {
        Session session = findSession(id);
        if (sessionRepository.existsByTokenAndIdNot(dto.getToken(), id)) {
            throw new ApplicationException(
                    ErrorCase.ALREADY_EXISTS,
                    "El token pertenece a otra sesión"
            );
        }
        sessionMapper.updateEntity(dto, session);
        Session updatedSession = sessionRepository.save(session);
        return sessionMapper.toResponseDTO(updatedSession);
    }
    public void delete(Long id) {
        Session session = findSession(id);
        sessionRepository.delete(session);
    }
}
