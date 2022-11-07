package com.example.myapplication

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object MiSerializador : Serializer<ListaTarjetas> {

    override val defaultValue: ListaTarjetas = ListaTarjetas.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ListaTarjetas {
        try{
            return ListaTarjetas.parseFrom(input)
        } catch(exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ListaTarjetas, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.tarjetasDataStore by dataStore(
    fileName = "datos",
    serializer = MiSerializador
)