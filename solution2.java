import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

    /*
     * Complete the 'optimalPoint' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY magic
     *  2. INTEGER_ARRAY dist
     */

    public static int optimalPoint(List<Integer> magic, List<Integer> dist) {
    // Write your code 
        int magic_sum = 0;
        int dist_sum =0;
        int ans = 0;
        int sum = 0;
        for(int i = 0;i < magic.size();i++){
            magic_sum += magic.get(i);
            dist_sum += dist.get(i);
            if(sum < 0){
                ans = i;
                sum = 0;
            }
            sum = sum + magic.get(i) - dist.get(i);            
        }
        if(magic_sum < dist_sum)
            return -1;
        return ans;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int magicCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> magic = IntStream.range(0, magicCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        int distCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> dist = IntStream.range(0, distCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.optimalPoint(magic, dist);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
