package com.sptech.LeaveANote;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {

        String sql = "SELECT * FROM `user`";

        List<User> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class)
        );

        List<UserModel> res = new ArrayList<>();

        for (User user : users) {

            UserModel um = new UserModel();
            um.setName(user.getName());
            um.setEmail(user.getEmail());
            um.setId(user.getId());
            res.add(um);

        }

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/login")
    public ResponseEntity<UserModel> validarCredenciais(@RequestBody User Ruser) {

        String sql = """
            SELECT *
            FROM `user`
            WHERE lower(email) = lower(?)
              AND lower(senha) = lower(?)
            """;

        List<User> users = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                Ruser.getEmail(),
                Ruser.getSenha()
        );

        if (users.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        User user = users.getFirst();

        UserModel um = new UserModel();
        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(user.getId());

        return ResponseEntity.ok(um);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank() || user.getEmail() == null ||
        user.getEmail().isBlank() || !user.getEmail().contains("@") ||
                !user.getEmail().contains(".") || user.getSenha().length() < 6){

            return ResponseEntity.status(409).build();

        }

        if (checkIfExistsEmail(user.getEmail())){

            return ResponseEntity.status(409).build();

        }

        String sql = """
                INSERT INTO `user` (name, email, senha)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update( con ->{
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,user.getName().toLowerCase());
            ps.setString(2,user.getEmail().toLowerCase());
            ps.setString(3,user.getSenha().toLowerCase());

            return ps;

        },keyHolder);

        UserModel um = new UserModel();
        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(keyHolder.getKeyAs(Integer.class));

        return ResponseEntity.status(201).body(um);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Integer id, @RequestBody User user) {

        if (!checkIfExistsById(id)) {
            return ResponseEntity.notFound().build();
        }

        String sql = """
                UPDATE `user`
                SET name = ?, email = ?, senha = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                user.getName().toLowerCase(),
                user.getEmail().toLowerCase(),
                user.getSenha().toLowerCase(),
                id
        );

        UserModel um = new UserModel();
        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(user.getId());

        return ResponseEntity.ok(um);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        if (!checkIfExistsById(id)) {
            return ResponseEntity.notFound().build();
        }

        String sql =
                "DELETE FROM `user` WHERE id = ?";

        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();

    }

    private Boolean checkIfExistsById(int id){

        String verificaSql =
                "SELECT COUNT(*) FROM `user` WHERE id = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                verificaSql,
                Integer.class,
                id
        );

        System.out.println(quantidade);

        if(quantidade >= 1){

            return true;

        }else {

            return false;

        }

    }

    private Boolean checkIfExistsEmail(String email){

        String verificaSql =
                "SELECT COUNT(*) FROM `user` WHERE lower(email) = lower(?)";

        Integer quantidade = jdbcTemplate.queryForObject(
                verificaSql,
                Integer.class,
                email
        );

        System.out.println(quantidade);

        if(quantidade >= 1){

            return true;

        }else {

            return false;

        }

    }

}

