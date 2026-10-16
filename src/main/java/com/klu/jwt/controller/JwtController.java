
package com.klu.jwt.controller;

import com.klu.jwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    // Generate a test JWT token
    @GetMapping("/test-token")
    public String generateToken() {
        return JwtUtil.generateTestToken();
    }

    // Validate JWT token and extract claims
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestBody Map<String, String> request) {

        String token = request.get("token");

        try {
            Claims claims = JwtUtil.validateToken(token);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Token Valid");
            response.put("username", claims.getSubject());
            response.put("roles", claims.get("roles"));

            return ResponseEntity.ok(response);

        } catch (JwtException | IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid Token");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
        }
    }
}
