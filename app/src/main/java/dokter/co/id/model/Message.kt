package dokter.co.id.model

class Message(var customerId : String, var dokerId : String, var nama_pasien : String, var isi_pesan : String, var nama_dokter : String, var tanggal : String, var type : String) {

    constructor() : this("","","","","", "", "") {

    }
}
