package com.ata.apps.budayaku.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class LicenseUtil {

	private static final String ALGORITHM = "PBEWithMD5AndDES";

	private static PBEParameterSpec pbeParamSpec;

	private static SecretKey pbeKey;

	public static void main(String args[]) {
		System.out.println("Generate key " + args[0]);
		//path, number max device ex : "d:/license.py" 10000
		writeMaxDevice(args[0], args[1]);

		String licenseNumDocs = getLicenseNumDocs(args[0]);
		System.out.println("licenseNum docs : " + licenseNumDocs);
	}

	static {
		byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
				(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
		pbeParamSpec = new PBEParameterSpec(salt, 20);

		PBEKeySpec pbeKeySpec = new PBEKeySpec(new char[] { '3', 'b', 'c', '0',
				'n', 'n', 'e', 'c', 't' });
		try {
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ALGORITHM);
			pbeKey = keyFac.generateSecret(pbeKeySpec);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(byte[] clearText)
			throws GeneralSecurityException {
		// Create PBE Cipher
		Cipher pbeCipher = Cipher.getInstance(ALGORITHM);

		// Initialize PBE Cipher with key and parameters
		pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

		// Encrypt the cleartext
		return pbeCipher.doFinal(clearText);
	}

	public static byte[] decrypt(byte[] clearText)
			throws GeneralSecurityException {
		// Create PBE Cipher
		Cipher pbeCipher = Cipher.getInstance(ALGORITHM);

		// Initialize PBE Cipher with key and parameters
		pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

		// Encrypt the cleartext
		return pbeCipher.doFinal(clearText);
	}

	public static void writeCryptedFile(OutputStream out, String text) {
		try {
			byte[] cipherText = encrypt(text.getBytes());
			out.write(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readCryptedFile(InputStream in) {
		String clearText = null;
		try {
			byte[] buf = new byte[255];
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(255);
			int len = in.read(buf);
			while (len > -1) {
				byteOut.write(buf, 0, len);
				len = in.read(buf);
			}

			byte[] cipherText = byteOut.toByteArray();
			byte[] resultText = decrypt(cipherText);
			clearText = new String(resultText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clearText;
	}

	public static String getLicenseExpiration(String key_file) {
		return getLicenseStatus(key_file, 0);
	}

	public static String getLicenseNumDocs(String key_file) {
		return getLicenseStatus(key_file, 1);
	}

	public static ArrayList<String> getHWMacAddress() {
		ArrayList<String> aResult = new ArrayList<String>();
		ArrayList<InetAddress> ipHostAddress = new ArrayList<InetAddress>();
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface
					.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				displayInterfaceInformation(netint, ipHostAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (!ipHostAddress.isEmpty()) {
				for (int i = 0; i < ipHostAddress.size(); i++) {
					NetworkInterface network = NetworkInterface
							.getByInetAddress(ipHostAddress.get(i));
					byte[] mac = network.getHardwareAddress();
					if (mac != null) {
						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < mac.length; j++) {
							sb.append(String.format("%02X%s", mac[j],
									(j < mac.length - 1) ? ":" : ""));
						}
						aResult.add(sb.toString().trim().toLowerCase());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aResult;
	}

	private static void displayInterfaceInformation(NetworkInterface netint,
			ArrayList<InetAddress> ipHostAddress) throws SocketException {
		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
			String tmp = inetAddress.toString();
			if (!tmp.contains(":")) {
				ipHostAddress.add(inetAddress);
			}
		}
	}

	public static String getApplicationID(String app_key, int field) {
		String result = null;
		try {
			FileInputStream in = new FileInputStream(app_key);
			String text = LicenseUtil.readCryptedFile(in);
			in.close();
			String[] token = text.split("\r\n");
			result = token[field];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String getLicenseStatus(String key_file, int field) {
		String result = null;
		try {
			FileInputStream in = new FileInputStream(key_file);
			String text = LicenseUtil.readCryptedFile(in);
			in.close();
			String[] token = text.split("\r\n");
			result = token[field];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void writeAppLicense(String app_key, String installationID) {
		String str = installationID;
		try {
			FileOutputStream out = new FileOutputStream(app_key);
			System.out.println("creating license in: " + app_key);
			writeCryptedFile(out, str);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void writeMaxDevice(String key_file, String docsNum) {
		String str = "\r\n" + docsNum;
		try {
			FileOutputStream out = new FileOutputStream(key_file);
			System.out.println("creating license in: " + key_file);
			writeCryptedFile(out, str);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}