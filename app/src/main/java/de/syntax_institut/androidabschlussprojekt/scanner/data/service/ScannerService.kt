package de.syntax_institut.androidabschlussprojekt.scanner.data.service


import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ScannerService() {

    private val scanner: BarcodeScanner = BarcodeScanning.getClient()


    @OptIn(ExperimentalGetImage::class)
    fun getAnalyzer(onBarcodeDetected: (String) -> Unit): ImageAnalysis.Analyzer {
        return ImageAnalysis.Analyzer { imageProxy: ImageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            if (barcode.format == Barcode.FORMAT_EAN_13 || barcode.format == Barcode.FORMAT_EAN_13) {
                                barcode.rawValue?.let { onBarcodeDetected(it) }
                            }
                        }
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }

    fun close() {
        scanner.close()
    }
}