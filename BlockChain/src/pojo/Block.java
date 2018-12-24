package pojo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {
	
	private Integer index;
	private long timestamp;
	private String previousHash ="";
	private String data;
	private String hash;
	private int nonce;
	
	
	public Block(int index,String previousHash, String data){
		this.index = index;
		this.timestamp = new Date().getTime();
		this.previousHash = previousHash;
		this.data = data;
		this.hash = generateHash();
	}
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		
		this.previousHash = previousHash;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public String generateHash(){
		
		String block = this.index.toString()+this.timestamp+this.previousHash.toString()+Integer.toString(this.nonce)+this.data.toString();
		MessageDigest message = null;
		try {
			message = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(message == null){
			return null;
		}
		message.update(block.getBytes(StandardCharsets.UTF_8));
		byte[] digest = message.digest();
		String code = String.format("%064x", new BigInteger(1,digest));
		
		return code;
	}
	
	public void mineBlock(int difficulty){
		
		String target = new String(new char[difficulty]).replace('\0', '0');//create string with difficulty
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = generateHash();
		}
		System.out.println("Block mined !! -" +hash );
	}
}
