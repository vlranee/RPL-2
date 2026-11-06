# StaRent Camp

Aplikasi desktop Java Swing untuk penyewaan alat camping. Praktikum Rekayasa Perangkat Lunak 2.

**Teknologi:** Java 21, Swing, MySQL, Maven, NetBeans 17

## Pertemuan 3: Login (JDBC)

- Project: `login`, database `db_login`, tabel `loginn`
- Login memakai JDBC (`PreparedStatement`)
- Validasi field kosong dan pesan jika username/password salah

## Pertemuan 4: Sign Up (Hibernate) Pelanggan & Sign Up Admin (StaRent)

- Project: `signup`, memakai database dan tabel yang sama dengan `login`
- Sign Up memakai Hibernate (entity `User`, `hibernate.cfg.xml`)
- Validasi: field kosong, konfirmasi password, username sudah dipakai
- Setelah sign up berhasil, pindah ke form Login (JDBC) dari project `login`

- Project: `starent`, database `db_starent`
- [x] Login admin (JDBC + BCrypt)
- [x] Dashboard dengan sidebar navigasi
- [ ] Sign Up Admin
- [ ] Pelanggan (tambah, ubah, hapus, cari)

## Cara Menjalankan

1. Nyalakan MySQL (XAMPP) dan buat database sesuai project.
2. Buka project di NetBeans, klik kanan → **Reload POM**.
3. Khusus `signup`: **Clean and Build** project `login` dulu.
4. Khusus `starent`: jalankan `SeedAdmin.java` sekali (akun `admin` / `admin123`).
5. **Clean and Build**, lalu **Run** (main class: `Main`).
