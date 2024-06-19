package com.developer.rozan.criby.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developer.rozan.criby.R
import com.developer.rozan.criby.data.local.entity.CryBabyEntity
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DELAY_1500L = 1500L
const val DELAY_2000L = 2000L

const val PAR1 = 1
const val PAR2 = 2

const val PARAM = "param"

const val AUDIO_FILEPATH = "audio_filepath"
const val DESCRIPTION = "description"

const val PREDICTION_RESPONSE = "prediction_response"

const val CRY_BABY_ENTITY = "cry_baby_entity"
const val HISTORY_AUDIO_ENTITY = "history_audio_entity"

enum class Participant {
    USER, MODEL, ERROR
}

fun showKeyboard(activity: Activity, view: View) {
    val input = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun closeKeyboard(activity: Activity, view: View) {
    val input: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.hideSoftInputFromWindow(view.windowToken, 0)
}

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun showSnackBar(activity: AppCompatActivity, message: String) {
    return Snackbar.make(activity.window.decorView.rootView, message, Snackbar.LENGTH_SHORT).show()
}

fun showToast(activity: AppCompatActivity, message: String) {
    return Toast.makeText(activity.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun formatText(response: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()
    val lines = response.split("\n")

    lines.forEach { line ->
        when {
            line.startsWith("*") -> {
                val formattedLine = line.replace("*", "").trim() + "\n"
                val start = spannable.length
                spannable.append("• $formattedLine")
                spannable.setSpan(
                    BulletSpan(100),
                    start,
                    spannable.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    spannable.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            else -> {
                spannable.append(line.trim() + "\n")
            }
        }
    }

    return spannable
}

fun formatDate(timestamp: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = sdf.parse(timestamp) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun getDate(): String {
    val sdf = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
    return sdf.format(Date())
}

fun addWavHeader(file: File, sampleRate: Int, channels: Int, bitsPerSample: Int) {
    val audioData = file.readBytes()
    val totalAudioLen = audioData.size
    val totalDataLen = totalAudioLen + 36
    val byteRate = sampleRate * channels * bitsPerSample / 8

    val header = ByteBuffer.allocate(44)
    header.order(ByteOrder.LITTLE_ENDIAN)
    header.put("RIFF".toByteArray())
    header.putInt(totalDataLen)
    header.put("WAVE".toByteArray())
    header.put("fmt ".toByteArray())
    header.putInt(16) // Subchunk1Size for PCM
    header.putShort(1) // AudioFormat (1 for PCM)
    header.putShort(channels.toShort())
    header.putInt(sampleRate)
    header.putInt(byteRate)
    header.putShort((channels * bitsPerSample / 8).toShort()) // BlockAlign
    header.putShort(bitsPerSample.toShort())
    header.put("data".toByteArray())
    header.putInt(totalAudioLen)

    val wavFile = File(file.parent, file.nameWithoutExtension + ".wav")
    FileOutputStream(wavFile).use { outputStream ->
        outputStream.write(header.array())
        outputStream.write(audioData)
    }
}

val listBabyCries = listOf(
    CryBabyEntity(
        "Lapar",
        "Tanda bayi lapar dapat dikenali melalui gerak tubuh, suara, dan ekspresi wajah yang ia tunjukkan. Ada tiga tahapan tanda bayi lapar yang bisa ditunjukkan oleh bayi, yaitu lapar tahap awal, lapar tahap aktif, dan sudah sangat lapar. Untuk tahu lebih jelasnya, simak penjelasan berikut ini yuk\n" +
                "\n" +
                "Tahap I : Tanda bayi lapar tahap awal\n" +
                "\n" +
                "1. Menggerakkan badan atau terlihat gelisah seperti susah tidur, meregangkan tubuhnya dan menjadi lebih banyak bergerak.\n" +
                "2. Membuka mulutnya secara terus-menerus dan terkadang bayi juga akan menjulurkan lidahnya. Jadi bisa dipastikan dengan menyentuh bibir Si-kecil, jika Ia membuka mulutnya saat disentuh, hal ini berarti Si-kecil memang lapar.\n" +
                "3. Menengok kanan dan kiri, Bayi yang sedang akan lapar akan menggerakkan kepalanya ke kanan dan kiri, seolah ia mencari makanan dan terkadang banyak berkedip dan menggerakkan matanya.\n" +
                "4. Melihat makanan dengan antusias, pada bayi yang sudah diberi MPASI, saat lapar ia akan tampak bersemangat ketika melihat makanan dan berusaha meraihnya.\n" +
                "\n" +
                "Tahap II: Tanda bayi lapar tahap aktif\n" +
                "Bila bunda tidak segera memberinya Asi atau makanan, bayi akan menunjukkan tanda lapar tahap aktif, seperti:\n" +
                "\n" +
                "1.Menggeliatkan badan terus-menerus, saat rasa laparnya semakin meningkat bayi akan lebih sering menggerakkan badannya, terlihat tidak nyaman dan semakin gelisah.\n" +
                "2.Menggerutu, bayi akan mengeluarkan suara menggerutu atau bahkan merengek ketika ia sedang lapar.\n" +
                "3.Memasukkan tangan ke mulut, bayi yang sedang lapar akan memasukkan jemarinya kedalam mulut atau mengisap jari atau kepalan tangannya.\n" +
                "\n" +
                "Tahap III: Tanda bayi sudah sangat lapar\n" +
                "Bila bayi sudah merasa sangat lapar, biasanya ia akan menunjukkan tanda-tanda berikut ini:\n" +
                "\n" +
                "1. Menangis, tangisan karena lapar aka lebih kencang dan lebih nyaring dari tangisan biasanya. jika ia tidak segera disusui atau diberi makan.\n" +
                "2. Mengetakkan badannya, bayi yang sudah sangat lapar biasanya akan mengentakkan badannya ke segala arah. Ini pertanda Si kecil kesal dan perlu segera disusui.\n" +
                "3. Kulit tubuh dan wajahnya memerah, Lantaran menangis dan mengentakkan badannya terus-menerus karena lapar, wajah dan kulit tubuh bayi bisa memerah. Jika sudah demikian, ia perlu disusui atau diberi makan secepatnya",
        "https://www.alodokter.com/bunda-ini-tanda-tanda-bayi-lapar",
        R.drawable.img_lapar
    ),
    CryBabyEntity(
        "Tidak nyaman",
        "Tanda bayi ketika tidak nyaman dapat di kenali melalui:\n" +
                "\n" +
                "1. Menangis atau merengek, bayi menangis juga kadang bisa jadi pertanda kalau Si-kecil sedang tidak nyaman.\n" +
                "2. Popok basah, bayi dengan popok basah akan merasakan kelembaban di area popoknya, yang bisa menimbulkan rasa tidak nyaman. Si-kecil juga akan terkadang menunjukkan ketidaknyamanan dengan menjadi gelisah, menggerak-gerakkan tubuh mereka. Beberapa bayi mungkin juga mencoba menggosok-gosokkan tubuh mereka ke permukaan atau memegang area popok mereka yang basah.",
        "https://www.alodokter.com/orang-tua-cerdas-harus-paham-alasan-bayi-menangis",
        R.drawable.img_tidak_nyaman
    ),
    CryBabyEntity(
        "Sendawa",
        "Sendawa pada bayi merupakan hal yang perlu diperhatikan oleh orang tua. Hal ini penting dilakukan karena bayi masih memerlukan bantuan orang tuanya untuk bersendawa agar dapat melepaskan gas dari perutnya. Berikut beberapa cara membuat Si-kecil bersendawa :\n" +
                "\n" +
                "1. Menggendong bayi merupakan cara membuat bayi bersendawa dengan cara menggendonnya dengan posisi dagu berada di bahu Bunda. Gunakan tangan untuk menahan bayi dan tangan lainnya untuk menepuk-nepuk bayi secara perlahan.\n" +
                "2. Menidurkan bayi di pangkuan dengan posisi tengkurap, buat posisi kepalanya sedikit lebih tinggi dari tubuhnya dan tepuk atau usap secara perlahan.\n" +
                "3. Memandikan bayi dengan air hangat tidak secara langsung membuat bayi untuk bersendawa. Namun, mandi dengan air hangat bisa membuat bayi merasa lebih rileks dan nyaman, yang pada gilirannya bisa membantu dalam proses pencernaan mereka.",
        "https://www.alodokter.com/pentingnya-sendawa-bagi-bayi",
        R.drawable.img_sendawa
    ),
    CryBabyEntity(
        "Sakit perut",
        "Bayi mungkin belum bisa menyampaikan secara pasti keluhan sakit perut yang sedang dirasakan, tapi ciri-ciri khas dapat Bunda perhatikan dari perubahan perilakunya. Beberapa ciri-ciri bayi sakit perut yang perlu diwaspadai di antaranya:\n" +
                "\n" +
                "1. Frekuensi menangis meningkat lebih dari biasanya, Bayi yang merasa sakit perut mungkin akan menangis lebih sering dan dengan intensitas yang lebih tinggi dari biasanya. Ini bisa menjadi tanda bahwa mereka merasa tidak nyaman atau kesakitan.\n" +
                "2. Muntah, ini bisa terjadi karena perut mereka terasa tidak nyaman atau karena ada masalah pencernaan yang menyebabkan reaksi muntah.\n" +
                "3. Menolak menyusu atau makan, Bayi yang sakit perut mungkin akan menolak menyusui atau makan dengan pola yang biasanya mereka lakukan. Ini bisa terjadi karena rasa tidak nyaman saat makan atau karena mereka merasa terlalu penuh.\n" +
                "4. Mengalami gangguan tidur, sakit perut bisa mengganggu tidur bayi. Si-kecil mungkin kesulitan tidur atau terbangun secara teratur karena ketidaknyamanan yang mereka rasakan.\n" +
                "5. Diare atau sembelit, perubahan dalam pola buang air besar, seperti diare atau sembelit, bisa menjadi tanda bahwa bayi mengalami masalah pencernaan atau sakit perut.\n" +
                "6. Tampak meringis, bayi yang sakit perut mungkin terlihat meringis atau mengepalkan bibir mereka. Ini bisa menjadi tanda bahwa mereka merasa tidak nyaman atau kesakitan.\n" +
                "7. Bahasa tubuh yang tegang, bayi yang sakit perut mungkin menampilkan bahasa tubuh yang tegang, seperti menegangkan otot atau kesulitan untuk tenang. Ini bisa menjadi reaksi alami terhadap rasa tidak nyaman yang mereka alami.",
        "https://www.haibunda.com/parenting/20230825214505-60-314393/mengenal-ciri-ciri-bayi-sakit-perut-penyebab-cara-mengatasinya",
        R.drawable.img_sakit_perut
    ),
    CryBabyEntity(
        "Lelah",
        "Berikut ini beberapa tanda awal rasa lelah pada bayi:\n" +
                "\n" +
                "1. Ekspresi wajah berubah: Dari bahagia menjadi lemas, menandakan bahwa bayi mulai lelah dan tidak nyaman.\n" +
                "2. Menggosok mata, rambut, telinga: Upaya bayi untuk menghilangkan kelelahan dengan menggosok area-area tersebut.\n" +
                "3. Menguap: Tanda umum kelelahan, bahkan pada bayi.\n" +
                "4. Menghindari kontak mata: Bayi menatap kosong ke atas, kehilangan minat pada interaksi sosial.\n" +
                "5. Mengencangkan tubuh: Tangannya mengepal dan kakinya tegang, menunjukkan ketegangan akibat kelelahan.\n" +
                "6. Kehilangan minat pada mainan atau makanan: Menandakan penurunan minat dan energi.\n" +
                "7. Menangis saat disusui: Mungkin karena kelelahan atau ketidaknyamanan.\n" +
                "8. Memukul atau membuang benda: Tanda frustrasi atau ketidaknyamanan.\n" +
                "9. Mengisap jempol: Upaya untuk menghibur diri dan menghilangkan stres.\n" +
                "10. Berpotensi menampakkan perilaku agresif: Seperti memukul, karena mereka belum bisa mengungkapkan perasaan mereka dengan kata-kata.",
        "https://www.vidoran.com/read/tentang-anak/kenali-tanda-tanda-bayi-lelah-dan-bahayanya",
        R.drawable.img_lelah
    )
)

//fun convertToWave(inputFile: String, outputFile: String) {
//    var fis: FileInputStream? = null
//    var fos: FileOutputStream? = null
//    try {
//        fis = FileInputStream(inputFile)
//        fos = FileOutputStream(outputFile)
//
//        val totalAudioLen = fis.channel.size()
//        val totalDataLen = totalAudioLen + 36
//
//        addWaveHeader(fos, totalAudioLen, totalDataLen)
//
//        val data = ByteArray(1024)
//        var bytesRead: Int
//        while (fis.read(data).also { bytesRead = it } != -1) {
//            fos.write(data, 0, bytesRead)
//        }
//
//        fis.close()
//        fos.close()
//    } catch (e: IOException) {
//        e.printStackTrace()
//    } finally {
//        fis?.close()
//        fos?.close()
//    }
//}
//
//private fun addWaveHeader(out: FileOutputStream, totalAudioLen: Long, totalDataLen: Long) {
//    val sampleRate = 44100
//    val channels = 1 // Mono
//    val byteRate = 16 * sampleRate * channels / 8
//
//    val header = ByteArray(44)
//
//    header[0] = 'R'.toByte()  // RIFF/WAVE header
//    header[1] = 'I'.toByte()
//    header[2] = 'F'.toByte()
//    header[3] = 'F'.toByte()
//    header[4] = (totalDataLen and 0xff).toByte()
//    header[5] = (totalDataLen shr 8 and 0xff).toByte()
//    header[6] = (totalDataLen shr 16 and 0xff).toByte()
//    header[7] = (totalDataLen shr 24 and 0xff).toByte()
//    header[8] = 'W'.toByte()
//    header[9] = 'A'.toByte()
//    header[10] = 'V'.toByte()
//    header[11] = 'E'.toByte()
//    header[12] = 'f'.toByte()  // 'fmt ' chunk
//    header[13] = 'm'.toByte()
//    header[14] = 't'.toByte()
//    header[15] = ' '.toByte()
//    header[16] = 16  // 4 bytes: size of 'fmt ' chunk
//    header[17] = 0
//    header[18] = 0
//    header[19] = 0
//    header[20] = 1  // format = 1 (PCM)
//    header[21] = 0
//    header[22] = channels.toByte()
//    header[23] = 0
//    header[24] = (sampleRate and 0xff).toByte()
//    header[25] = (sampleRate shr 8 and 0xff).toByte()
//    header[26] = (sampleRate shr 16 and 0xff).toByte()
//    header[27] = (sampleRate shr 24 and 0xff).toByte()
//    header[28] = (byteRate and 0xff).toByte()
//    header[29] = (byteRate shr 8 and 0xff).toByte()
//
//    out.write(header, 0, 44)
//}