package com.cj.aginghelper.frameworks.helper

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class ResourcesHelper(private val context: Context){
    private val root = context.applicationContext.dataDir.absolutePath
    var progress = mutableIntStateOf(0)

    fun checkResourceExists(): Boolean{
        val directory = File("${root}/data")

        if(!directory.exists()){
            Files.createDirectories(Paths.get("${root}/data"))
        }

        val file = File("${root}/data/sam_ffhq_aging.pt")

        return if(file.exists()){
            val fileSize = file.length()

            fileSize == 2270547237
        } else{
            false
        }
    }

    fun extractResource(completion: (Boolean) -> Unit){
        val file = File("${root}/data/sam_ffhq_aging.pt")

        if(file.exists()){
            file.delete()
        }

        GlobalScope.launch(Dispatchers.IO){
            Thread{
                try{
                    val compressedModel = context.applicationContext.assets.open("sam_ffhq_aging.zip")
                    val zipInputStream = ZipInputStream(BufferedInputStream(compressedModel))
                    var zipEntry: ZipEntry
                    val buffer = ByteArray(1024)
                    var count: Int
                    var totalBytesRead = 0L
                    val totalSize = 2270547237L

                    while(true){
                        zipEntry = zipInputStream.nextEntry ?: break

                        val fileName = zipEntry.name
                        val outputStream = FileOutputStream("${root}/data/${fileName}")
                        var bytesRead = 0L

                        while(true){
                            count = zipInputStream.read(buffer)

                            if(count == -1){
                                break
                            }

                            outputStream.write(buffer, 0, count)
                            bytesRead += count.toLong()
                            totalBytesRead += count.toLong()

                            progress.intValue = (totalBytesRead * 100 / totalSize).toInt()
                        }

                        outputStream.close()
                        zipInputStream.closeEntry()
                    }

                    zipInputStream.close()
                    completion(true)
                    return@Thread

                } catch(e: Exception){
                    e.printStackTrace()
                    completion(false)
                    return@Thread
                }
            }.start()
        }
    }
}