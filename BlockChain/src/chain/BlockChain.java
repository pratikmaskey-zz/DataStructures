package chain;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import pojo.Block;

public class BlockChain {

	static ArrayList<Block> gCoin = new ArrayList<>();
	private static int difficulty = 1;
	
	public BlockChain() {
		this.createGenesisBlock();
	}
	
	public Block createGenesisBlock(){
		
		return new Block(0, "", "This is Genesis");
	}
	
	public Block getLatestBlock(){
		return gCoin.get(gCoin.size() - 1);
	}
	
	public Boolean isChainValid(){
		
		Block prevousBlock;
		Block currentBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for (int i = 1; i < gCoin.size(); i++) {
			
			currentBlock = gCoin.get(i);
			prevousBlock = gCoin.get(i-1);
			
			if(currentBlock.getPreviousHash().equals(prevousBlock.getHash())){
				System.out.println("Chain is valid !!!");
				return true;
			}
			
			if(!currentBlock.getPreviousHash().equals(prevousBlock.getHash())){
				System.out.println("Invalid Chain !!!");
				return false;
			}
			
			if(!currentBlock.getHash().substring(0 ,difficulty).equals(hashTarget)){
				System.out.println("This block has not been mined !!!");
				return false;
			}
			
		}
		
		return true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		BlockChain chain = new BlockChain();
		gCoin.add(chain.createGenesisBlock());
		System.out.println("Trying to mine block 1 !!");
		gCoin.get(0).mineBlock(difficulty);
		
		gCoin.add(new Block (1, gCoin.get(gCoin.size() -1).getHash(),"First Transaction"));
		Thread.sleep(1000);
		System.out.println("Trying to mine block 2 !!");
		gCoin.get(1).mineBlock(difficulty);
		
		gCoin.add(new Block (2, gCoin.get(gCoin.size() -1).getHash(),"Second Transaction"));
		System.out.println("Trying to mine block 3 !!");
		gCoin.get(2).mineBlock(difficulty);
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(gCoin);		
		System.out.println(blockchainJson);
		System.out.println("Is chain valid :"+ chain.isChainValid());
	}

}
