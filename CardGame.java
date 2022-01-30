import java.util.Collections;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;

public class CardGame {
    public static void main(String[] args) {

        ArrayList<Card> arr_card_pack = new ArrayList<>(); //Card ArrayList 생성

        int number[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        String shape[] = {"spade", "heart", "clover", "diamond"};

        for (int i = 0; i < 13; i++) {//i->0~12, number[i]=1~13
            for (int j = 0; j < 4; j++) { //j->0~3, shape[j]=spade~diamond
                Card temp = new Card();
                temp.number = number[i];
                temp.shape = shape[j];
                arr_card_pack.add(temp); //객체 temp를 목록에 추가
            }
        }
        //------------------------------------------------------------ArrrayList통해 52장 카드 구현

        System.out.print("게임 참여인원 수를 입력하세요.(2~4) : ");
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();//참여인원 수 입력받기

        ArrayList<Player> player_list=new ArrayList<>(); //Player ArrayList 생성
        for(int i=0;i<num;i++){
            Scanner sc2 = new Scanner(System.in);
            System.out.println("이름을 입력하세요");
            String name = sc2.next();
            System.out.println("나이를 입력하세요");
            int age = sc2.nextInt();
            System.out.println("직업을 입력하세요");
            String job = sc2.next();
            Player ptemp=new Player(name, age, job);
            player_list.add(ptemp); //객체 ptemp를 목록에 추가
        }
        System.out.println("플레이어의 수는 "+player_list.get(0).playerCount);
        //-----------------------------------------------------게임 참여인원 수를 입력받고, 정적 변수를 이용해 플레이어 수를 표시

        Collections.shuffle(player_list);//플레이어 리스트 섞기->랜덤성 부여
        System.out.println("~순서를 섞고 있습니다~");
        System.out.print("순서: ");
        for(int i=0;i<num;i++){
            System.out.print((i+1)+". "+player_list.get(i).name+" ");
        }
        System.out.println();
        //------------------------------------------------------------------------랜덤으로 플레이어의 순서 결정하기

        for(int i=0;i<num;i++) {
            System.out.print(player_list.get(i).name+"이(가) 카드를 뽑았습니다. 뽑은 카드는 [ ");
            for (int j = 0; j < 10; j++) { //10번 반복
                player_list.get(i).Card_draw(arr_card_pack); //카드 1장 뽑음
                System.out.print((player_list.get(i).c_list).get(j).number + " " +
                        (player_list.get(i).c_list).get(j).shape + ". "); //뽑은 카드 출력
            }
            System.out.print("]");
            System.out.println();
        }
        //--------------------------------------------------각 플레이어는 처음에 10장의 카드를 랜덤하게 뽑습니다.

        Player openCard = new Player("open",0,""); //바닥에 공개하는 카드-무조건 1개
        openCard.Card_draw(arr_card_pack);
        System.out.println("\n\n공개된 카드는 " + openCard.c_list.get(0).number + " " + openCard.c_list.get(0).shape + "\n");

        //--------------------------------------------------남아있는 카드 중 한장을 뽑아 바닥에 공개합니다.
        System.out.println("카드덱의 카드 개수: " + arr_card_pack.size());

        while(true) {
            for(int i=0;i<num;i++) {//플레이어 수만큼 반복
                System.out.print(player_list.get(i).name+": ");
                if (player_list.get(i).Card_canPut(arr_card_pack, openCard.c_list.get(0)) == true){
                    Scanner scanner1 = new Scanner(System.in);
                    Scanner scanner2 = new Scanner(System.in);
                    Scanner scanner3 = new Scanner(System.in);

                    System.out.print("\n" + player_list.get(i).name + "은(는) 카드를 낼건가요? (Yes/No로 대답해주세요.) ");
                    String answer = scanner3.nextLine();
                    if (answer.equals("Yes")) {
                        System.out.print(player_list.get(i).name + "이(가) 낼 카드의 숫자를 입력하세요: ");
                        int num1 = scanner1.nextInt();
                        System.out.print(player_list.get(i).name + "이(가) 낼 카드의 모양을 입력하세요: ");
                        String shape1 = scanner2.nextLine();
                        player_list.get(i).Card_putout(num1, shape1, arr_card_pack, openCard.c_list);
                    }
                    else if (answer.equals("No")) {//답이 No->카드를 내지 않고 뽑는다
                        System.out.println("카드덱에서 카드를 뽑습니다.");
                        player_list.get(i).Card_draw(arr_card_pack);
                    }
                }
                else if (player_list.get(i).Card_canPut(arr_card_pack, openCard.c_list.get(0)) == false)
                {
                    System.out.println(player_list.get(i).name + "이(가) 낼 카드가 없으므로 카드덱에서 카드를 뽑습니다.");
                    player_list.get(i).Card_draw(arr_card_pack);
                }
                if (player_list.get(i).c_list.size() == 0)
                {
                    System.out.println("★"+player_list.get(i).name + "이(가) 이겼습니다!★");
                    System.exit(0);
                }
                System.out.println("카드덱의 카드 개수: " + arr_card_pack.size());
                System.out.println(player_list.get(i).name + "의 카드 개수: " + player_list.get(i).c_list.size());
                System.out.println("\n공개된 카드는 " + openCard.c_list.get(0).number + " "
                        + openCard.c_list.get(0).shape + "\n");
            }
            //--------------------------------------------------오픈된 카드를 보고 플레이어는 카드를 내거나 카드덱에서 뽑습니다.
        }

    }
}
class Card{
    public String shape;
    public int number;
} //카드 클래스

class Player{
    public ArrayList<Card> c_list; //ArrayList변수
    public String name; //이름
    private int age;//나이
    private String job;//직업
    public static int playerCount=0; //정적변수, 플레이어의 수
    Player(String name, int age, String job){ //생성자
        c_list=new ArrayList<>();
        this.name=name;
        this.age=age;
        this.job=job;
        playerCount++;
    }
    //카드를 카드덱에서 뽑는 경우
    public void Card_draw(ArrayList<Card> list){ //c_list 개수 증가
        if (list.size() == 0) { //공개된 카드를 제외한 남아있는 카드가 없는 경우에는 뽑을 수 없다
            System.out.println("카드를 더 이상 뽑을 수 없습니다.");
            return;
        }
        Random rd=new Random(); //랜덤 객체 생성
        c_list.add(list.remove(rd.nextInt(list.size()))); //카드를 한장 뽑으면 list 카드 개수는 -1이 되고 Player클래스의 카드는 +1
    }
    //카드를 낼 수 있는지 오픈카드와 비교해서 확인하는 함수
    public boolean Card_canPut(ArrayList<Card> list, Card temp){ //c_list에서 카드를 낼 수 있으면 true
        int num=0;
        for(int i=0; i<c_list.size(); i++){
            if(temp.number==c_list.get(i).number||temp.shape.equals(c_list.get(i).shape)) {
                System.out.print(c_list.get(i).number + " " + c_list.get(i).shape + " ");
                num++;
            }
        }
        if(num>0)
            return true;
        else
            return false;
    }
    //카드를 내는 경우
    public void Card_putout(int num, String shape, ArrayList<Card> decklist, ArrayList<Card> temp) {
        for (int i = 0; i < c_list.size(); i++) {
            if (num == c_list.get(i).number && shape.equals(c_list.get(i).shape)) {
                decklist.add(temp.remove(0));//기존의 openCard는 지우고 decklist에는 추가
                temp.add(c_list.remove(i)); //낼 카드를 c_list에서는 지우고 openCard에 추가
            }
        }
    }
} //Player클래스