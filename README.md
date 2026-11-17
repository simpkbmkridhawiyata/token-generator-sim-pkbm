# Token Generator SIM PKBM

Aplikasi Android WebView untuk menampilkan halaman Google Apps Script Token Generator yang diberikan pengguna.

## Membuat APK tanpa Android Studio (melalui GitHub Actions)

1. Masuk atau buat akun GitHub.
2. Buat repository baru, misalnya `token-generator-sim-pkbm`.
3. Unggah seluruh isi folder proyek ini ke repository (termasuk folder tersembunyi `.github`).
4. Buka tab **Actions** pada repository.
5. Jika diminta, pilih **I understand my workflows, go ahead and enable them**.
6. Pilih workflow **Build APK - Token Generator SIM PKBM**, lalu klik **Run workflow**.
7. Tunggu sampai proses selesai dengan status hijau.
8. Buka hasil run tersebut, cari bagian **Artifacts**, lalu unduh `Token-Generator-SIM-PKBM-APK`.
9. Ekstrak ZIP artifact. Di dalamnya terdapat `app-debug.apk`. Kirim APK itu ke HP Android dan instal.

## Identitas aplikasi

- Nama: Token Generator SIM PKBM
- Application ID: id.kridhawiyata.tokengenerator
- Versi: 1.0.0
- Minimum Android: Android 6.0 (API 23)
- Koneksi internet diperlukan.

## Catatan

APK debug ini cocok untuk pengujian dan instalasi internal. Untuk distribusi lebih luas, buat APK release yang ditandatangani dengan signing key yang disimpan aman. Jangan mengunggah ke repository file rahasia atau private signing key.
