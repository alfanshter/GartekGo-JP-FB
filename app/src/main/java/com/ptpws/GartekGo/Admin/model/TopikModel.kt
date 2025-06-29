import com.google.firebase.Timestamp

data class TopikModel(
    var id: String = "",
    var nama: String ? = null,
    var semester : String = "",
    var file_materi : String ? = null,
    var nomor : Int ? = null,
    var nama_file : String? = null,
    var uploadedMateriAt: Timestamp? = null,
    //materi
    var soal : String? = null,
    var materi : String? = null,
    var vidio : String? = null,


)