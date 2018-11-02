package Premier_Paquet;

public class Max_Value {
	static int max_value(int[] nums) {   // using dynamic programming
	    int len = nums.length;
	    if(len == 0) return 0;
	    int[] dp = new int[len + 1];
	    dp[1] = nums[0];
	    for(int i = 2; i <= len; ++i){
	        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
	    }
	    return dp[len];
	}

	public static void main(String[] args) {
		int[] nums = {2, 7, 9, 3, 1};   // define the input
		System.out.println(max_value(nums));   // output the result
	}
}
