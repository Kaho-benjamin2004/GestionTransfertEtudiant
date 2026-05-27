package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.advice;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.MessageResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.ValidationErrorResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MessageResponseDTO> handleBusinessException(BusinessException ex) {
        HttpStatus status = switch (ex.getCode()) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "AUTH_FAILED", "INVALID_TOKEN" -> HttpStatus.UNAUTHORIZED;
            case "ALREADY_EXISTS" -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
        return ResponseEntity.status(status).body(
                MessageResponseDTO.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .statusCode(status.value())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(
                ValidationErrorResponseDTO.builder()
                        .message("Erreur de validation")
                        .errors(errors)
                        .statusCode(400)
                        .build()
        );
    }
}