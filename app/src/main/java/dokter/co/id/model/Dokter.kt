package dokter.co.id.model

class Dokter(var dokterId : String, var nama : String, var email : String, var alamat : String, var no_telp : String, var tanggal_lahir : String, var role: String) {

    constructor() : this("","","","","", "","") {

    }
}
