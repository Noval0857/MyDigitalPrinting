package umbjm.ft.inf.mydigitalprinting.item

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

class CenterCropTransformation : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val targetWidth = 1700 // Sesuaikan dengan lebar yang diinginkan
        val targetHeight = 1700 // Sesuaikan dengan tinggi yang diinginkan

        val width = source.width
        val height = source.height

        // Perhitungan startX dan startY untuk memastikan tidak ada nilai negatif
        val startX = if (width > targetWidth) (width - targetWidth) / 2 else 0
        val startY = if (height > targetHeight) (height - targetHeight) / 2 else 0

        // Menyesuaikan targetWidth dan targetHeight agar tidak melebihi dimensi gambar sumber
        val adjustedTargetWidth = if (width < targetWidth) width else targetWidth
        val adjustedTargetHeight = if (height < targetHeight) height else targetHeight

        // Buat bitmap baru dengan ukuran yang sudah disesuaikan
        val result = Bitmap.createBitmap(source, startX, startY, adjustedTargetWidth, adjustedTargetHeight)
        if (result != source) {
            source.recycle()
        }
        return result
    }

    override fun key(): String {
        return "centerCrop"
    }
}