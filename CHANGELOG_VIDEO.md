# Changelog - Multiple Videos Feature

## Perubahan yang Dilakukan

### 1. Model Data (TopikModel.kt)
- **Ditambahkan**: `VideoItem` data class untuk menyimpan informasi setiap video
  - `url`: URL download video dari Firebase Storage
  - `nama`: Nama file video
  - `path`: Path video di Firebase Storage
  - `urutan`: Urutan video (0, 1, 2, ...)
  
- **Ditambahkan**: Field `videos: List<VideoItem>?` di TopikModel
- **Backward Compatible**: Field lama (`file_video`, `nama_video`, `path_video`) tetap ada untuk kompatibilitas

### 2. Admin - Upload Multiple Videos (TambahVidioDialog_New.kt)
**Fitur Baru**:
- Admin bisa upload **TIDAK TERBATAS** jumlah video per topik
- Setiap video bisa ditambah, dihapus, atau diganti
- Progress upload menampilkan:
  - Video ke berapa yang sedang diupload (Video 1/3, Video 2/3, dst)
  - Persentase progress upload
- Video disimpan dalam format array di Firestore

**Cara Penggunaan Admin**:
1. Klik topik yang ingin ditambahkan video
2. Dialog akan menampilkan daftar video yang sudah ada (jika ada)
3. Klik **"Tambah Video"** untuk menambah video baru
4. Untuk setiap video bisa:
   - **Ganti**: Klik icon ganti untuk memilih video lain
   - **Hapus**: Klik icon hapus untuk menghapus video dari daftar
5. Klik **"SIMPAN"** untuk upload semua video

### 3. Admin - Tampilan List Video (TambahVidioScreen.kt)
- Card topik sekarang menampilkan jumlah video yang sudah diupload
- Contoh: "3 Video" atau "Tidak ada video"

### 4. Siswa - Sequential Video Playback (VidioScreen.kt)
**Fitur Baru**:
- Video ditampilkan secara **BERURUTAN**
- Video berikutnya **TERKUNCI** sampai video sebelumnya selesai ditonton
- Progress tracking: "Video 1 dari 3", "Video 2 dari 3", dst

**Cara Kerja untuk Siswa**:
1. Siswa melihat daftar semua video
2. Hanya video pertama yang bisa ditonton (yang lain terkunci 🔒)
3. Setelah video 1 selesai ditonton, otomatis berpindah ke video 2
4. Video 2 sekarang bisa ditonton, yang lain masih terkunci
5. Proses berlanjut sampai semua video selesai
6. Setelah SEMUA video selesai, baru muncul tombol **"SELANJUTNYA"** untuk ke soal

**Indikator Status Video**:
- ✅ **Hijau**: Video sudah selesai ditonton
- 🎬 **Biru**: Video sedang ditonton
- 🔒 **Abu-abu**: Video masih terkunci

### 5. Database Structure (Firestore)

**Collection: topik/{topikId}**
```json
{
  "videos": [
    {
      "url": "https://storage.googleapis.com/...",
      "nama": "Video1.mp4",
      "path": "public/1234567890.mp4",
      "urutan": 0
    },
    {
      "url": "https://storage.googleapis.com/...",
      "nama": "Video2.mp4", 
      "path": "public/1234567891.mp4",
      "urutan": 1
    }
  ]
}
```

**Collection: users/{uid}/topik/{topikId}**
```json
{
  "videos_watched": 2,  // Berapa video yang sudah selesai ditonton
  "vidio": "1"         // "1" jika semua video selesai, "0" jika belum
}
```

## Keunggulan Sistem Baru

1. **Fleksibel**: Guru bisa upload video sebanyak apapun yang dibutuhkan
2. **Terstruktur**: Video ditonton secara berurutan, tidak bisa skip
3. **Progress Tracking**: Sistem mencatat video mana saja yang sudah ditonton
4. **User Friendly**: UI yang jelas menunjukkan status setiap video
5. **Backward Compatible**: Data video lama (single video) tetap bisa ditampilkan

## Catatan Penting

- Video harus ditonton sampai selesai agar bisa lanjut ke video berikutnya
- Progress disimpan per user, jadi setiap siswa punya progress sendiri
- Admin bisa mengganti atau menambah video kapan saja
- Video disimpan di Firebase Storage di folder "public/"

