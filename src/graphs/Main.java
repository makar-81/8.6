package graphs;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
        String temp;

        FileReader fr = new FileReader("./test1.txt");
        FileWriter wr = new FileWriter("./test2.txt");

        BufferedWriter  bw = new BufferedWriter(wr);
        BufferedReader br = new BufferedReader(fr);

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            List<List<String>> final_store = new ArrayList<>();
            List<List<String>> begin_store = new ArrayList<>();
            int line_num = 0;

            try {
            while ((temp = br.readLine()) != null) {
                while (!temp.isEmpty()) {
                    int pos1 = temp.indexOf("\t");
                    int pos2 = temp.indexOf("\t",pos1+1);
                    ArrayList<String> line = new ArrayList<String>();
                    if (pos1 > 0 && pos2>0) {
                            line.add(temp.substring(0,pos1));
                            line.add(temp.substring(pos1+1,pos2));
                            line.add(temp.substring(pos2+1,temp.length()));
                            begin_store.add(line);
                        }
                    temp="";
                    }
                }

                while (!begin_store.isEmpty()) {
                    //int start = 0;
                    List<String> temp_list = new ArrayList<>(begin_store.get(0));
                    String double_lines ="";
                    begin_store.remove(0);
                    Double sum = new Double(0);
                    if (temp_list.get(2).equals("{}")){
                        sum = Double.parseDouble(temp_list.get(1));
                    } else temp_list.set(1,"0.000");
                        for (Integer j=0;j<begin_store.size();j++){
                        if ((begin_store.get(j).get(0).equals(temp_list.get(0))) && (begin_store.get(j).get(2).equals("{}"))) {
                            sum = sum + Double.parseDouble(begin_store.get(j).get(1));
                            temp_list.set(1,sum.toString());
                            double_lines = double_lines + j.toString() + ",";
                        }
                        else if ((begin_store.get(j).get(0).equals(temp_list.get(0)))) {
                            temp_list.set(2,(begin_store.get(j).get(2)));
                            double_lines = double_lines + j.toString() + ",";
                        }
                    }

                    // здесь конечная обработка и добавление в финальный массив результата
                    int iter=0;
                    while (!double_lines.isEmpty() && begin_store.size() > 0) {
                        int pos4 = double_lines.indexOf(",");
                        if (pos4 > 0){
                            begin_store.remove(Integer.parseInt(double_lines.substring(0,pos4))-iter);
                            double_lines = double_lines.substring(pos4+1,double_lines.length());
                            iter++;
                        }
                    }
                    Double alpha=0.100;
                    Double N = 5.000;
                    Double rank = alpha*(1/N) + (1-alpha)*Double.parseDouble(temp_list.get(1));
                    Double newDouble = new BigDecimal(rank).setScale(3, RoundingMode.HALF_EVEN).doubleValue();
                    DecimalFormatSymbols s = new DecimalFormatSymbols();
                    s.setDecimalSeparator('.');
                    DecimalFormat myFormatter = new DecimalFormat("0.000",s);
                    String output = myFormatter.format(newDouble);
                    temp_list.set(1,output);
                    if (!temp_list.get(2).equals("{}")) final_store.add(temp_list);
                }


                for (int j = 0; j< final_store.size();j++){
        //            System.out.println((final_store.get(j)).get(0) + "\t" + (final_store.get(j)).get(1) + "\t" + (final_store.get(j)).get(2) + "\n");
                    bw.write((final_store.get(j)).get(0) + "\t" + (final_store.get(j)).get(1) + "\t" + (final_store.get(j)).get(2) + "\n");
                    bw.flush();
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

