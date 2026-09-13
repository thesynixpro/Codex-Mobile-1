package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Codex Mobile", appName)
  }

  @Test
  fun `test signing key and cert loading`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val assetManager = context.assets

    // Verify debug.pk8 and debug.x509.pem
    val keyBytes = assetManager.open("templates/debug.pk8").use { it.readBytes() }
    val kf = java.security.KeyFactory.getInstance("RSA")
    val privateKey = kf.generatePrivate(java.security.spec.PKCS8EncodedKeySpec(keyBytes))
    org.junit.Assert.assertNotNull(privateKey)
    org.junit.Assert.assertEquals("RSA", privateKey.algorithm)

    val cf = java.security.cert.CertificateFactory.getInstance("X.509")
    val cert = assetManager.open("templates/debug.x509.pem").use {
      cf.generateCertificate(it) as java.security.cert.X509Certificate
    }
    org.junit.Assert.assertNotNull(cert)
    org.junit.Assert.assertNotNull(cert.publicKey)
  }
}
