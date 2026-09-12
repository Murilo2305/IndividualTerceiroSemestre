package com.sptech.LeaveANote;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            um.setBirth(user.getBirth());
            um.setFunction(user.getFunction());
            um.setGenre(user.getGenre());
            um.setRecieveEMails(user.getRecieveEmails());

            res.add(um);
        }

        return ResponseEntity.ok(res);
    }


    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody User Ruser) {

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
                Ruser.getPassword()
        );

        if (users.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        User user = users.getFirst();

        UserModel um = new UserModel();

        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(user.getId());
        um.setBirth(user.getBirth());
        um.setFunction(user.getFunction());
        um.setGenre(user.getGenre());
        um.setRecieveEMails(user.getRecieveEmails());

        return ResponseEntity.ok(um);
    }


    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody User user) {

        if (user.getName() == null ||
                user.getName().isBlank() ||

                user.getEmail() == null ||
                user.getEmail().isBlank() ||
                !user.getEmail().contains("@") ||
                !user.getEmail().contains(".") ||

                user.getPassword() == null ||
                user.getPassword().length() < 6 ||

                user.getBirth() == null ||
                user.getBirth().isAfter(LocalDate.now()) ||

                user.getFunction() == null ||
                user.getGenre() == null ||
                user.getRecieveEmails() == null ||

                checkIfExistsEmail(user.getEmail())) {

            return ResponseEntity.status(409).build();
        }

        String sql = """
                INSERT INTO `user`
                (name, email, senha, birth, `function`, genre, recieveEmails)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();



        jdbcTemplate.update(con -> {

            PreparedStatement ps = con.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, user.getName().toLowerCase());
            ps.setString(2, user.getEmail().toLowerCase());
            ps.setString(3, user.getPassword().toLowerCase());

            // LocalDate -> java.sql.Date
            ps.setDate(4, java.sql.Date.valueOf(user.getBirth()));

            ps.setString(5, user.getFunction().toLowerCase());
            ps.setString(6, user.getGenre().toLowerCase());
            ps.setBoolean(7, user.getRecieveEmails());

            return ps;

        }, keyHolder);


        UserModel um = new UserModel();

        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(keyHolder.getKeyAs(Integer.class));
        um.setBirth(user.getBirth());
        um.setFunction(user.getFunction());
        um.setGenre(user.getGenre());
        um.setRecieveEMails(user.getRecieveEmails());

        return ResponseEntity.status(201).body(um);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Integer id, @RequestBody User user) {

        if (!checkIfExistsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (user.getName() == null ||
                user.getName().isBlank() ||
                user.getEmail() == null ||
                user.getEmail().isBlank() ||
                user.getPassword() == null ||
                user.getPassword().length() < 6) {

            return ResponseEntity.badRequest().build();
        }

        String sql = """
                UPDATE `user`
                SET name = ?,
                    email = ?,
                    senha = ?,
                    birth = ?,
                    `function` = ?,
                    genre = ?,
                    recieveEmail = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                user.getName().toLowerCase(),
                user.getEmail().toLowerCase(),
                user.getPassword().toLowerCase(),

                // LocalDate -> java.sql.Date
                java.sql.Date.valueOf(user.getBirth()),

                user.getFunction().toLowerCase(),
                user.getGenre().toLowerCase(),
                user.getRecieveEmails(),

                id
        );


        UserModel um = new UserModel();

        um.setName(user.getName());
        um.setEmail(user.getEmail());
        um.setId(id);
        um.setBirth(user.getBirth());
        um.setFunction(user.getFunction());
        um.setGenre(user.getGenre());
        um.setRecieveEMails(user.getRecieveEmails());

        return ResponseEntity.ok(um);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        if (!checkIfExistsById(id)) {
            return ResponseEntity.notFound().build();
        }

        String sql = "DELETE FROM `user` WHERE id = ?";

        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

    private Boolean checkIfExistsById(int id) {

        String verificaSql =
                "SELECT COUNT(*) FROM `user` WHERE id = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                verificaSql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade >= 1;
    }

    private Boolean checkIfExistsEmail(String email) {

        String verificaSql =
                "SELECT COUNT(*) FROM `user` WHERE lower(email) = lower(?)";

        Integer quantidade = jdbcTemplate.queryForObject(
                verificaSql,
                Integer.class,
                email
        );

        return quantidade != null && quantidade >= 1;
    }
}