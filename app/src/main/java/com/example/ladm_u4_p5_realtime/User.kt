package com.example.ladm_u4_p5_realtime

import com.google.firebase.database.IgnoreExtraProperties

// Solo para objetos Transacionales (De kotlin a Realtime)

@IgnoreExtraProperties
data class User (val userName:String?=null, val email:String ?=null){

}