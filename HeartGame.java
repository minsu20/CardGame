//2020112377 컴퓨터공학전공 김민수
//HeartGame.java

import java.util.Collections;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;

class Card{
    public String shape;
    public String value;// 2, 3, 4, ..., J, Q, K, A
    public int pri_index; //1~13 13에 가까울수록 우선순위는 높아짐
    public int own_index; //소유하고 있는 플레이어의 인덱스
} //카드 클래스

class Player{
    public ArrayList<Card> c_list; //ArrayList 플레이 하는 카드덱 ->매 라운드마다 바뀜
    public ArrayList<Card> collect_list; //플레이하면서 모은 카드 ->매 라운드마다 바뀜
    public String name; //이름 -> 라운드가 바뀌어도 안바뀜
    public int score;//점수 -> 라운드가 바뀌어도 안바뀜

    Player(String name){ //생성자
        c_list=new ArrayList<>();
        collect_list=new ArrayList<>();
        this.name=name;
        score=0;
    }
    //카드를 카드덱에서 뽑는 경우
    public void Card_draw(ArrayList <Card> decklist){ //c_list 개수 증가
        Random rd=new Random(); //랜덤 객체 생성
        c_list.add(decklist.remove(rd.nextInt(decklist.size()))); //카드를 한장 뽑으면 list 카드 개수는 -1이 되고 Player클래스의 카드는 +1
    }

    //하트 카드를 낼 수 있는지->이전 트릭에서 카드가 나온 경우에만 이 함수 호출하고 opencardlist 초기화시킴
    public boolean Card_heartcanPut(ArrayList <Card> open){ //c_list에서 카드를 낼 수 있으면 true
        for(int i=0;i<open.size();i++){
            if(open.get(i).shape.equals("Heart"))
                return true; //opencard에서 모양이 heart가 하나라도 있으면 true
        }
        return false;
    }

    //카드를 공개-> open_list에 추가 및 카드 리턴, 하트카드 비교
    public void Card_putout(boolean what, ArrayList<Card> openlist) { //하트카드를 낼 수 있는지와 openlist를 매개변수로
        System.out.println(name + "님 카드를 내세요.");
        System.out.print("가지고 있는 카드는 [");
        for (int i = 0; i < c_list.size(); i++) {
            System.out.print(" "+c_list.get(i).shape + " " + c_list.get(i).value + ".");
        }
        System.out.print("]  ");

        System.out.print("모은 카드는 [");
        for (int i = 0; i < collect_list.size(); i++) {
            System.out.print(" "+collect_list.get(i).shape + " " + collect_list.get(i).value + ".");
        }
        System.out.print("]");

        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);

        System.out.println();
        System.out.println("어떤 카드를 내겠습니까?");
        while (true) {
            System.out.print("모양: ");
            String shape = scanner1.next();

            System.out.print("숫자/글자: ");
            String value = scanner2.next();

            int size=c_list.size();
            if (what == false) {//heartcard 못 내는 경우

                if (shape.equals("Heart"))
                    System.out.println("하트카드는 낼 수 없습니다.");
                else {
                    int i=0;
                    for (i = 0; i < c_list.size(); i++) {
                        if (value.equals(c_list.get(i).value) && shape.equals(c_list.get(i).shape)) {
                            openlist.add(c_list.remove(i)); //c_list에는 지우고 openCard에 추가
                            break;
                        }
                    }
                    if (size != c_list.size()) { //c_list에서 지워졌으면
                        break;
                    } else {
                        System.out.println("그 카드는 소유하고 있지 않습니다.");
                    }
                }
            } else {
                int i=0;
                for (i = 0; i < c_list.size(); i++) {
                    if (value.equals(c_list.get(i).value) && shape.equals(c_list.get(i).shape)) {
                        openlist.add(c_list.remove(i)); //c_list에는 openCard에 추가
                        break;
                    }
                }
                if (size != c_list.size()) { //c_list에서 지워졌으면
                    break;
                } else {
                    System.out.println("그 카드는 소유하고 있지 않습니다.");
                }
            }
        }
    }

    //카드 가져감
    public void Card_collect(ArrayList<Card> openlist){ //collect_list 개수 증가->우열이 가장 높은 카드가 있어서 카드를 먹음
        for(int i=0;i<openlist.size();i++) {
            collect_list.add(openlist.get(i)); // num개의 카드를 collect_list에 추가
        }
    }


    //Club2를 갖고 있으면 true
    public boolean Card_isClub2(){ //c_list에서 Clubs2가 있으면 true
        for(int i=0; i<c_list.size(); i++) {
            if (c_list.get(i).value.equals("Two") && c_list.get(i).shape.equals("Club")) {//플레이어의 카드덱에 Clubs 2가 있는지 check
                return true;
            }
        }
        return false;
    }

    //점수 계산
    public void calculateScore(){
        for(int i=0;i<collect_list.size();i++){
            if(collect_list.get(i).shape.equals("Heart"))
                score+=1;
            else if(collect_list.get(i).shape.equals("Spade")&&collect_list.get(i).value.equals("Q"))
                score+=3;
            else if(collect_list.get(i).shape.equals("Spade")&&collect_list.get(i).value.equals("A"))
                score+=5;
        }
    }

    //Q가 4장인지
    public boolean isQ4(){
        int number=0;//Q개수
        for(int i=0; i<collect_list.size();i++){
            if(collect_list.get(i).value.equals("Q"))
                number++;
        }
        if(number==4)
            return true;
        else
            return false;
    }

    //
} //Player클래스


public class HeartGame {

    public static void main(String[] args) {

        int roundcount=1;
        int num = 0, dividingCard = 0;
        while (true) {
            System.out.print("게임 참여인원 수를 입력하세요.(4~6) : ");
            Scanner sc = new Scanner(System.in);
            num = sc.nextInt();//참여인원 수 입력받기
            if (num >= 4 && num <= 6)
                break;
            else
                System.out.println("4~6 사이로 입력해주세요.");
        }

        System.out.println("플레이어의 수는 " + num + "명 입니다.");

        if (num == 4)
            dividingCard = 13;
        else if (num == 5)
            dividingCard = 10;
        else if (num == 6)
            dividingCard = 8;

        ArrayList<Player> player_list=new ArrayList<>(); //Player ArrayList 생성

        for (int i = 0; i < num; i++) {
            Scanner sc = new Scanner(System.in);
            System.out.print((i+1) + "번째 플레이어의 이름을 입력하세요: ");
            String name = sc.next();
            Player ptemp=new Player(name);
            player_list.add(ptemp); //객체 ptemp를 목록에 추가
        }

        //--------------------------------------------------게임 참여인원 수를 입력받고, 각 플레이어마다 나누어가지는 카드 개수 다름

        while (true) { //라운드 진행
            System.out.println();
            System.out.println("["+roundcount+"라운드]");

            Collections.shuffle(player_list);//플레이어 리스트 섞기->랜덤성 부여
            System.out.println();
            System.out.println("~임의로 순서를 부여하고 있습니다.~");
            System.out.print("순서: ");
            for(int i=0;i<num;i++){
                if(i==0)
                    System.out.print(player_list.get(i).name);
                else
                    System.out.print("--> "+player_list.get(i).name);
            }
            System.out.println();
            //------------------------------------------------------------------------랜덤으로 플레이어의 순서 결정하기

            int startIndex = 10;
            System.out.println();
            while(true) {
                ArrayList<Card> arr_card_pack = new ArrayList<>(); //매 라운드마다 Card ArrayList 생성해야함

                String value[] = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "J", "Q", "K", "A"};
                String shape[] = {"Spade", "Heart", "Club", "Diamond"};
                int index[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

                for (int i = 0; i < 13; i++) {//i->0~12
                    for (int j = 0; j < 4; j++) { //j->0~3, shape[j]=Spades~diamond
                        Card temp = new Card();
                        temp.value = value[i];
                        temp.pri_index = index[i];
                        temp.shape = shape[j];
                        temp.own_index = 0;
                        arr_card_pack.add(temp); //객체 temp를 목록에 추가
                    }
                }
                //---------------------------------------------------------ArrrayList통해 value가 2<3<...<J<Q<K<A인 52장 카드 구현

                for (int i = 0; i < num; i++) {
                    System.out.println(player_list.get(i).name + "님이 " + dividingCard + "장의 카드를 가져갑니다.");
                    for (int j = 0; j < dividingCard; j++) { //dividngCard번 반복
                        player_list.get(i).Card_draw(arr_card_pack); //카드 1장 뽑음
                        player_list.get(i).c_list.get(j).own_index = i; //카드를 가지고 있는 플레이어의 인덱스
                    }

                }
                //----------------------------------------------------------------각 플레이어는 처음에 같은 수의 카드를 랜덤하게 뽑는다.


                for (int i = 0; i < num; i++) {
                    if (player_list.get(i).Card_isClub2() == true)
                        startIndex = i;
                }
                if (startIndex != 10)
                    break;
                else{ //클로버 2를 가진 사람이 아무도 없으면 다시 뽑는다.
                    System.out.println("클로버 2를 가진 사람이 아무도 없으므로 다시 뽑습니다.");
                    System.out.println();
                    for(int i=0;i<num;i++) {
                        player_list.get(i).c_list.clear();
                    }
                }
            }

            //------------------------------------------------------------------------클로버 2를 가진 사람이 맨 처음으로 스타트


            ArrayList<Card> OpenCard = new ArrayList<>(); //각 트릭마다 opencard->최대 num개이다

            while (true) { //트릭 진행
                System.out.println();
                System.out.println(player_list.get(startIndex).name + "님 부터 카드를 냅니다.");
                for (int i = startIndex; i < num; i++) {//플레이어 수만큼 반복
                    if (i == startIndex) {//처음 순서이면 하트 내는데 제약이 있음
                        if (player_list.get(i).Card_heartcanPut(OpenCard) == true) { //하트카드 낼 수 있으면
                            OpenCard.clear(); //오픈카드리스트 비움
                            player_list.get(i).Card_putout(true, OpenCard);
                        } else { //하트카드 낼 수 없으면
                            OpenCard.clear(); //오픈카드리스트 비움
                            player_list.get(i).Card_putout(false, OpenCard);
                        }
                    } else { //처음 순서 아니면 하트 항상 낼 수 있음
                        player_list.get(i).Card_putout(true, OpenCard);
                    }
                }

                for (int i = 0; i < startIndex; i++) { //처음 순서 아니면 하트 항상 낼 수 있음
                    player_list.get(i).Card_putout(true, OpenCard);
                }
                //--------------------------------------------------------------------------카드를 공개함(한 트릭)-openCard num개

                int maxvalue = OpenCard.get(0).pri_index;//비교할 때 가장 큰 수
                int maxIndex = startIndex;//비교할 때 가장 큰 수를 가지는 객체의 인덱스
                for (int i = 1; i < OpenCard.size(); i++) {
                    if (OpenCard.get(i).shape.equals(OpenCard.get(0).shape)) {
                        if (OpenCard.get(i).pri_index > maxvalue) {
                            maxvalue = OpenCard.get(i).pri_index;
                            maxIndex = OpenCard.get(i).own_index;
                        }
                    }
                }
                //----------------------------------------------------------------------------카드 비교->가져가는 플레이어 인덱스 찾기
                System.out.println();
                System.out.print("공개된 카드는 [");
                for(int i=0;i<OpenCard.size();i++){
                    System.out.print(player_list.get(OpenCard.get(i).own_index).name+"님의 "+OpenCard.get(i).shape+" "+OpenCard.get(i).value+ ". ");
                    OpenCard.get(i).own_index=maxIndex; //오픈카드는 모두 maxIndex player 소유가 됨
                }
                System.out.print("] ");
                System.out.println();

                System.out.println("가장 높은 등급의 카드를 낸 사람은 "+player_list.get(maxIndex).name+"님 입니다.");

                player_list.get(maxIndex).Card_collect(OpenCard); //카드 가져감->OpenCard 리스트는 그대로임

                startIndex=maxIndex; //maxIndex부터 카드 냄

                for (int i = 0; i < num; i++) {
                    if (player_list.get(i).isQ4() == true) { //매 트릭마다 Q4개를 가지고 있는 플레이어가 있는지 검사
                        System.out.println(player_list.get(i).name + "님이 Q 4장을 가지고 있어서 이겼습니다! (게임 종료)");
                        return; //종료
                    }
                }
                if(player_list.get(0).c_list.size()==0) { //카드를 다 소진했으면
                    System.out.println();
                    System.out.println(roundcount+"라운드가 종료되었습니다.");
                    roundcount++;
                    for (int i = 0; i < num; i++) {
                        player_list.get(i).calculateScore();
                        System.out.println(player_list.get(i).name+"님의 점수: "+player_list.get(i).score);
                    }
                    int m_score = player_list.get(0).score;
                    int m_index = 0;
                    ArrayList <Player> winner_list=new ArrayList<>();
                    for (int i = 0; i < num; i++) {
                        if (player_list.get(i).score >= 30) { //30점을 한명이라도 넘기면
                            for (int j = 1; j < num; j++) {
                                if (player_list.get(j).score < m_score) {
                                    m_score = player_list.get(j).score;
                                    m_index = j;
                                }
                            }
                            for(int k=0;k<player_list.size();k++) { //승리자가 여러명인 경우도 있으므로
                                if(player_list.get(k).score==m_score){ //가장 낮은 점수와 같은 경우
                                    winner_list.add(player_list.get(k));//winner_List에 추가
                                }
                            }
                            System.out.print("우승자는 ");
                            for(int w=0;w<winner_list.size();w++){
                                System.out.print(winner_list.get(w).name+" ");
                            }
                            System.out.println("입니다! (게임 종료)");
                            return;
                        }
                    }
                    System.out.println("점수가 30점을 넘는 사람이 없으므로 다음 라운드가 시작됩니다.");
                    for(int i=0;i<num;i++){
                        player_list.get(i).c_list.clear();
                        player_list.get(i).collect_list.clear(); //라운드 종료 후 c_list, collect_List 지우기
                    }
                    break; //단 하나도 30점을 못 넘기면 다음 라운드 진행
                }
            }
        }
    }
}
