package com.sptech.LeaveANote;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final JdbcTemplate jdbcTemplate;

    public NoteController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Note>> list() {

        String sql = """
                SELECT id, msg, fkUser
                FROM note
                """;

        List<Note> notes = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Note.class)
        );

        if (notes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(notes);
    }

    @GetMapping("/users/{fkUser}")
    public ResponseEntity<List<Note>> searchByUser(@PathVariable Integer fkUser) {

        String sql = """
            SELECT id, msg, fkUser
            FROM note
            WHERE fkUser = ?
            """;

        List<Note> notes = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Note.class),
                fkUser
        );

        if (notes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> cadastrar(
            @RequestBody Note note) {

        String sql = """
                INSERT INTO note (msg, fkUser)
                VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, note.getMsg());
            ps.setObject(2, note.getFkUser());

            return ps;

        }, keyHolder);

        note.setId(keyHolder.getKeyAs(Integer.class));

        return ResponseEntity.status(201).body(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody Note note) {

        String sql = """
                UPDATE note
                SET msg = ?, fkUser = ?
                WHERE id = ?
                """;

        int linhasAfetadas = jdbcTemplate.update(
                sql,
                note.getMsg(),
                note.getFkUser(),
                id
        );

        if (linhasAfetadas == 0) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        System.out.println("ssssssssssssssssss");

        String sql = """
                DELETE FROM note
                WHERE id = ?
                """;

        int linhasAfetadas = jdbcTemplate.update(sql, id);

        if (linhasAfetadas == 0) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(204).build();
    }
}