// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: Cryptor.java 3919 2008-01-16 04:36:52Z xf $
// created at:2005-10-18
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.util.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对文件加密.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 */
public class Cryptor {
	private static final Log log = LogFactory.getLog(Cryptor.class);
	/**
	 * 加密写入文件的IO流,对IO流进行了加密处理,使其产生加密文件.
	 * @param outFileName 输出的文件名称. 
	 * @param keyFile 密钥的文件名称.
	 * @return 加密后的输出流.
	 */
	public static OutputStream encryptFileIO(
		String outFileName,
		String keyFile) {
		if(keyFile==null){
			try {
				return new FileOutputStream(outFileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		SecretKey key = null;

		//从密钥文件读取密钥
		ObjectInputStream keyis;
		try {
			keyis = new ObjectInputStream(new FileInputStream(keyFile));
			key = (SecretKey) keyis.readObject();
			keyis.close();
		} catch (FileNotFoundException e) {
			log.error("file not found!", e);
			throw new RuntimeException("file not found", e);
		} catch (IOException e) {
			log.error("io occour exception", e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			log.error("Class Not Found  exception", e);
			throw new RuntimeException(e);
		}

		//用key产生Cipher
		Cipher cipher = null;
		//加密要用Cipher来实现

		try {
			cipher = Cipher.getInstance("DES");
			//设置加密模式

			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		//从对话框中取得要加密的文件

		//读入并加密文件
		CipherOutputStream out = null;
		try {
			out =
				new CipherOutputStream(
					new BufferedOutputStream(new FileOutputStream(outFileName)),
					cipher);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return out;

	}
	/**
	 * 解密读取文件的IO流.对IO流进行解密,使其读到真正的文件.
	 * @param inputFileName 需要读取的文件名称.
	 * @param keyFile 密钥文件的名称.
	 * @return 解密后的IO流.
	 */
	public static InputStream dencryptFileIO(
		String inputFileName,
		String keyFile) {
			if(keyFile==null){
				try {
					return new FileInputStream(new File(inputFileName));
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		SecretKey key = null;

		ObjectInputStream keyis;
		try {
			keyis = new ObjectInputStream(new FileInputStream(keyFile));
			key = (SecretKey) keyis.readObject();

			keyis.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		//用key产生Cipher
		Cipher cipher = null;

		try {
			//设置算法,应该与加密时的设置一样
			cipher = Cipher.getInstance("DES");
			// 设置解密模式
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		File file = new File(inputFileName);

		try {

			//输入流
			CipherInputStream in =
				new CipherInputStream(
					new BufferedInputStream(new FileInputStream(file)),
					cipher);
			return in;

		} catch (Exception e) {
			throw new RuntimeException(e);

		}

	}
}
