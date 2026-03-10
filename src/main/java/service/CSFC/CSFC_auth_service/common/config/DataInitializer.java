package service.CSFC.CSFC_auth_service.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import service.CSFC.CSFC_auth_service.model.entity.Permission;
import service.CSFC.CSFC_auth_service.model.entity.Roles;
import service.CSFC.CSFC_auth_service.model.entity.Users;
import service.CSFC.CSFC_auth_service.repository.PermissionsRepository;
import service.CSFC.CSFC_auth_service.repository.RolesRepository;
import service.CSFC.CSFC_auth_service.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolesRepository rolesRepository;
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (rolesRepository.count() > 0) {
            log.info("Data already initialized, skipping...");
            return;
        }

        log.info("Initializing seed data...");

        // ── Permissions ──────────────────────────────────────────────
        List<String[]> permDefs = List.of(
            new String[]{"USER_VIEW",      "Xem danh sách người dùng"},
            new String[]{"USER_CREATE",    "Tạo người dùng"},
            new String[]{"USER_UPDATE",    "Cập nhật người dùng"},
            new String[]{"USER_DELETE",    "Xóa người dùng"},
            new String[]{"ROLE_VIEW",      "Xem danh sách role"},
            new String[]{"ROLE_CREATE",    "Tạo role"},
            new String[]{"ROLE_UPDATE",    "Cập nhật role"},
            new String[]{"ROLE_DELETE",    "Xóa role"},
            new String[]{"PERMISSION_VIEW",   "Xem permissions"},
            new String[]{"PERMISSION_ASSIGN", "Gán permission cho role"}
        );

        List<Permission> permissions = permDefs.stream().map(d ->
            Permission.builder().name(d[0]).description(d[1]).build()
        ).toList();
        permissionsRepository.saveAll(permissions);
        log.info("Saved {} permissions", permissions.size());

        // ── Roles ────────────────────────────────────────────────────
        // ADMIN — tất cả permissions
        Roles adminRole = new Roles();
        adminRole.setName("ADMIN");
        adminRole.setCreateDate(LocalDateTime.now());
        adminRole.setPermissions(new HashSet<>(permissions));
        rolesRepository.save(adminRole);

        // USER — chỉ xem
        Set<Permission> userPerms = new HashSet<>();
        permissions.stream()
            .filter(p -> p.getName().endsWith("_VIEW"))
            .forEach(userPerms::add);

        Roles userRole = new Roles();
        userRole.setName("USER");
        userRole.setCreateDate(LocalDateTime.now());
        userRole.setPermissions(userPerms);
        rolesRepository.save(userRole);

        log.info("Saved roles: ADMIN, USER");

        // ── Admin user ────────────────────────────────────────────────
        if (usersRepository.count() == 0) {
            Users admin = new Users();
            admin.setName("Super Admin");
            admin.setEmail("admin@csfc.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setCreateDate(LocalDateTime.now());
            admin.setIsActive(true);
            admin.setIsFirstLogin(false);
            admin.setRole(adminRole);
            usersRepository.save(admin);
            log.info("Created admin user: admin@csfc.com / Admin@123");
        }

        log.info("Seed data initialized successfully.");
    }
}
