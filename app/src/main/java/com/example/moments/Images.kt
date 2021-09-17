package com.example.moments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.moments.databinding.ActivityImagesBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.io.ByteArrayOutputStream

class Images : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding
    private lateinit var imageView : ImageView
    private var fileUri : Uri? = null
    private val REQUEST_CODE = 100
    private val REQUEST_WRITE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Images"

        MobileAds.initialize(this){}

        val addRequest = AdRequest.Builder().build()
        val bannerView = binding.bannerAd
        bannerView.loadAd(addRequest)


        val capture = binding.Capture
        capture.setOnClickListener {
            checkForPermissions(android.Manifest.permission.CAMERA,"Camera",REQUEST_CODE)


        }
        val share = binding.share
        share.setOnClickListener {

            checkForPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "Storage", REQUEST_WRITE)


        }
        imageView = binding.circleImageView


    }

    private fun getImageUri(context: Context, bit: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bit.compress(Bitmap.CompressFormat.JPEG,100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver,bit,"Image",null)
        return  Uri.parse(path)
    }

    private fun getBitmapFromView(imageView: ImageView): Bitmap? {
        val bitmap = Bitmap.createBitmap(imageView.width, imageView.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        imageView.draw(canvas)
        return bitmap

    }
    private fun checkForPermissions(permission : String, name : String, requestCode : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    if (requestCode == REQUEST_WRITE){val bit : Bitmap? = getBitmapFromView(imageView)
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "image/*"
                        intent.putExtra(Intent.EXTRA_STREAM,getImageUri(this,bit!!))
                        startActivity(Intent.createChooser(intent,"Share Image"))}
                    if (requestCode == REQUEST_CODE){
                        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(i,REQUEST_CODE)
                    }


                }
                shouldShowRequestPermissionRationale(permission) -> showDialogue(permission, name, requestCode)


                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innerCheck(name: String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"$name permission Denied", Toast.LENGTH_SHORT).show()
            }else{
                if (requestCode == REQUEST_WRITE){val bit : Bitmap? = getBitmapFromView(imageView)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM,getImageUri(this,bit!!))
                startActivity(Intent.createChooser(intent,"Share Image"))}
                if (requestCode == REQUEST_CODE){
                    val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(i,REQUEST_CODE)
                }



            }
        }
        when(requestCode){
            REQUEST_CODE -> innerCheck("Camera")
            REQUEST_WRITE -> innerCheck("Storage")
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showDialogue(permission: String, name: String, requestCode: Int) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your $name is required if you want to share your moments")
            setTitle("Permission required")
            setPositiveButton("Ok"){ _, _ ->
                ActivityCompat.requestPermissions(this@Images, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            val pic : Bitmap? = data?.getParcelableExtra("data")

            imageView.setImageBitmap(pic)
        }
    }
}
