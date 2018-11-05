/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lsmichel.cardmanager;

import akka.Done;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.KillSwitch;
import akka.stream.Materializer;
import akka.stream.alpakka.jms.JmsConsumerSettings;
import akka.stream.alpakka.jms.JmsProducerSettings;
import akka.stream.alpakka.jms.javadsl.JmsConsumer;
import akka.stream.alpakka.jms.javadsl.JmsProducer;
import akka.stream.javadsl.*;
import com.lsmichel.cardmanager.ICardMessages.CardCreateActionPerformet;
import com.lsmichel.cardmanager.ICardMessages.InfoCard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author lathsessakpamichel
 */
public class CardregistryActor extends AbstractActor {
     private  Map<String , Integer> currentMapCount = new  HashMap<String , Integer> ();
     private  Map<String ,List<Map<String ,Object>>> curretcards = new HashMap<String ,List<Map<String ,Object>>>();
    //private  Map<String, Object> last
    //private static ActorSystem system = ActorSystem.create();
    //private static Materializer materializer = ActorMaterializer.create(system);
    
    
    static Props props() {
    return Props.create(CardregistryActor.class);
  }
    
    @Override
    public Receive createReceive() {
           
            return receiveBuilder()
           /* .match(ICardMessages.CardCreate.class, (ICardMessages.CardCreate cardInfo) -> {
               ActorRef sender =  getSender();
               List<String> lstring = new ArrayList<String>();
               final List<Card> cards = new ArrayList<Card>();
               final SlickSession session = SlickSession.forConfig("slick-mysql");
               ActorSystem system = ActorSystem.create();
               Materializer materializer = ActorMaterializer.create(system);
               system.registerOnTermination(session::close);
               final String insertCardQuery = CardregistryActor.SetCardDataInQuery(cardInfo.getCard()) ;
               System.out.println(insertCardQuery);
               final CompletionStage<Done> done = Slick.source(
                session,
                //insertCardQuery,
                "SELECT card_id  FROM card_tbl" ,
                (SlickRow row) -> {
                    lstring.add("test");
                    //System.out.println(row.toString());
                    return new CardCreateActionPerformet("created" , cardInfo.getCard().getCardNumber());
                })
                .runWith(Sink.ignore(), materializer);
               
                done.whenComplete(
                    (value, exception) -> {
                    System.out.println(lstring.size());
                    CardCreateActionPerformet cardCreateActionPerformet = new CardCreateActionPerformet("created" , cardInfo.getCard().getCardNumber());
                    sender
                    .tell(cardCreateActionPerformet ,getSelf());
                    system.terminate();
               });
               
            })*/
            .match(ICardMessages.CardCreate.class, (ICardMessages.CardCreate cardInfo) -> {
                 ActorSystem system = ActorSystem.create();
                 Materializer materializer = ActorMaterializer.create(system);
                 ActorRef sender =  getSender();
                 if(cardInfo!= null && cardInfo.getCard() !=null && ! cardInfo.getCard().getCardNocaisse().isEmpty()){
                     Map<String, Object> icardIn = new HashMap<>();
                     Card card = cardInfo.getCard();
                     if(card.getCardUserFname() !=null && !card.getCardUserFname().isEmpty() )
                         icardIn.put("cardUserFname", card.getCardUserFname());
                     if(card.getCardUserLname() !=null && !card.getCardUserLname().isEmpty())
                         icardIn.put("cardUserLname", card.getCardUserLname() );
                     if(card.getCardDateEtabishment() !=null && !card.getCardDateEtabishment().isEmpty())
                          icardIn.put("cardDateEtabishment", card.getCardDateEtabishment() );
                     if(card.getCardDateEpiration() !=null && ! card.getCardDateEpiration().isEmpty())
                          icardIn.put("cardDateEpiration", card.getCardDateEpiration() );
                     if(card.getCardLocationEtabishment() !=null && ! card.getCardLocationEtabishment().isEmpty())
                          icardIn.put("cardLocationEtabishment", card.getCardLocationEtabishment() );
                     if(card.getCardImatriculation() !=null && ! card.getCardImatriculation().isEmpty())
                          icardIn.put("cardImatriculation", card.getCardImatriculation() );
                     if(card.getCardNumber() !=null && ! card.getCardNumber().isEmpty())
                          icardIn.put("cardNumber", card.getCardNumber()  );
                     if(card.getCardUserSex() !=null && ! card.getCardUserSex().isEmpty())
                          icardIn.put("cardUserSex", card.getCardUserSex() );
                     if(card.getCardUserPhoto() !=null && ! card.getCardUserPhoto().isEmpty())
                          icardIn.put("cardUserPhoto", card.getCardUserPhoto());
                     if(card.getCardUserAdress() !=null && ! card.getCardUserAdress().isEmpty())
                          icardIn.put("cardUserAdress", card.getCardUserAdress());
                     if(card.getCardUserPofession() !=null && ! card.getCardUserPofession().isEmpty())
                          icardIn.put("cardUserPofession", card.getCardUserPofession());
                     if(card.getCardUserFatherName() !=null && ! card.getCardUserFatherName().isEmpty())
                          icardIn.put("cardUserFatherName", card.getCardUserFatherName());
                     if(card.getCardUserFatherBirthDate() !=null && ! card.getCardUserFatherBirthDate().isEmpty())
                          icardIn.put("cardUserFatherBirthDate", card.getCardUserFatherBirthDate());
                     if(card.getCardUserMatherBirthDate() !=null && ! card.getCardUserMatherBirthDate().isEmpty())
                          icardIn.put("cardUserMatherBirthDate", card.getCardUserMatherBirthDate());
                     if(card.getCardUserMatherName() !=null && ! card.getCardUserMatherName().isEmpty() )
                          icardIn.put("cardUserMatherName", card.getCardUserMatherName());
                     if(card.getCardUserBirthDate() !=null && ! card.getCardUserBirthDate().isEmpty())
                          icardIn.put("cardUserBirthDate", card.getCardUserBirthDate());
                     if(card.getCardUserBrithPlace() !=null && ! card.getCardUserBrithPlace().isEmpty())
                          icardIn.put("cardUserBrithPlace", card.getCardUserBrithPlace());
                     if(card.getCardNocaisse() !=null && ! card.getCardNocaisse().isEmpty())
                          icardIn.put("cardNocaisse", card.getCardNocaisse());
                      ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin" , "admin", "tcp://172.20.0.2:61616");
                      Sink<Map<String, Object>, CompletionStage<Done>> jmsSink =
                      JmsProducer.mapSink(JmsProducerSettings.create(connectionFactory).withQueue("clientID_"+card.getCardNocaisse()));
                      CompletionStage<Done> finished = Source.single(icardIn).runWith(jmsSink, materializer);
                      try {
                          finished.whenComplete(
                             (value, exception) -> {
                                 CardCreateActionPerformet cardCreateActionPerformet=null;
                                if(exception ==null){
                                      IncreasePosQueNum(card.getCardNocaisse());
                                    cardCreateActionPerformet = new CardCreateActionPerformet("created", cardInfo.getCard().getCardNumber() , false , "" );
                                }
                                else {
                                    cardCreateActionPerformet = new CardCreateActionPerformet("error" , cardInfo.getCard().getCardNumber() , true , exception.getStackTrace().toString()); 
                                }
                                sender
                                .tell(cardCreateActionPerformet ,getSelf());
                                
                             }).toCompletableFuture().join();
                       } finally {
                         system.terminate();
                       }
                 }
            })
            .match(PosCard.class, (PosCard posCardInfo) -> {
                ActorRef sender =  getSender();
               if(curretcards.containsKey("clientID_"+posCardInfo.getCardNocaisse()) 
                       && curretcards.get("clientID_"+posCardInfo.getCardNocaisse()).size() >0 ){
                         Map<String ,Object> card = curretcards.get("clientID_"+posCardInfo.getCardNocaisse()).get(0);
                         InfoCard infoCard = new InfoCard(false, "", card);
                         curretcards.get("clientID_"+posCardInfo.getCardNocaisse()).remove(card);
                         sender.tell(infoCard ,getSelf());
               }
               else {
                       ActorSystem system = ActorSystem.create();
                       Materializer materializer = ActorMaterializer.create(system);
                       int tn = GetCurrentPosQueNum(posCardInfo.getCardNocaisse());
                       ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin" , "admin", "tcp://172.20.0.2:61616");
                       Source<Map<String, Object>, KillSwitch> jmsSource =
                       JmsConsumer.mapSource(
                       JmsConsumerSettings.create(connectionFactory).withQueue("clientID_"+posCardInfo.getCardNocaisse()));
                       CompletionStage<List<Map<String, Object>>> resultStage =
                       jmsSource.map(s-> {AddCardTocurrent(posCardInfo.getCardNocaisse(),s); return s ;}).take(tn).runWith(Sink.seq(), materializer);
                       try {
                          List<Map<String, Object>> result = resultStage.toCompletableFuture().get(2000, TimeUnit.MILLISECONDS);
                         
                          InfoCard infoCard = new InfoCard(false, "", result.get(0)); 
                          DecreasePosQueNum(posCardInfo.getCardNocaisse());
                          sender.tell(infoCard ,getSelf());
                      }catch(Exception ex){
                        ex.printStackTrace();
                        InfoCard infoCard = new InfoCard(true, ex.getMessage(), null); 
                        sender.tell(infoCard ,getSelf());
                    }
                }
            })
            .build();
    }
    
    public static class PosCard{
         private final String cardNocaisse ; 
         
         public PosCard() {
            this.cardNocaisse = "test";
        }
        public PosCard(String cardNocaisse) {
            this.cardNocaisse = cardNocaisse;
        }

        public String getCardNocaisse() {
            return cardNocaisse;
        }
          
    }
    
     public static class Card {
        private final  int cardid;
        private final String cardUserFname ;
        private final String cardUserLname ;
        private final String cardDateEtabishment ;
        private final  String cardDateEpiration ;
        private final  String cardLocationEtabishment ;
        private final  String cardImatriculation ;
        private final String cardNumber ;
        private final String cardUserSex ;
        private final  String cardUserPhoto  ;
        private final String cardUserAdress ;
        private final String cardUserPofession ;
        private final String cardUserFatherName ;
        private final String cardUserFatherBirthDate ;
        private final String cardUserMatherBirthDate ;
        private final String cardUserMatherName ;
        private final String cardNocaisse ; 
        private final String cardUserBirthDate;
        private final String cardUserBrithPlace;
        
        public Card() {
            this.cardid = 1;
            this.cardUserFname = "";
            this.cardUserLname = "";
            this.cardDateEtabishment = "";
            this.cardDateEpiration = "";
            this.cardLocationEtabishment = "";
            this.cardImatriculation = "";
            this.cardNumber = "";
            this.cardUserSex = "";
            this.cardUserPhoto = "";
            this.cardUserAdress = "";
            this.cardUserPofession = "";
            this.cardUserFatherName = "";
            this.cardUserFatherBirthDate = "";
            this.cardUserMatherBirthDate = "";
            this.cardUserMatherName = "";
            this.cardNocaisse = "";
            this.cardUserBirthDate="";
            this.cardUserBrithPlace="";
        }

        public Card(int cardid, String cardUserFname, String cardUserLname, String cardDateEtabishment, 
                String cardDateEpiration, String cardLocationEtabishment, String cardImatriculation, String cardNumber, 
                String cardUserSex, String cardUserPhoto, String cardUserAdress, String cardUserPofession, 
                String cardUserFatherName, String cardUserFatherBirthDate, String cardUserMatherBirthDate, 
                String cardUserMatherName, String cardNocaisse , String cardUserBirthDate ,String cardUserBrithPlace ) {
            this.cardid = cardid;
            this.cardUserFname = cardUserFname;
            this.cardUserLname = cardUserLname;
            this.cardDateEtabishment = cardDateEtabishment;
            this.cardDateEpiration = cardDateEpiration;
            this.cardLocationEtabishment = cardLocationEtabishment;
            this.cardImatriculation = cardImatriculation;
            this.cardNumber = cardNumber;
            this.cardUserSex = cardUserSex;
            this.cardUserPhoto = cardUserPhoto;
            this.cardUserAdress = cardUserAdress;
            this.cardUserPofession = cardUserPofession;
            this.cardUserFatherName = cardUserFatherName;
            this.cardUserFatherBirthDate = cardUserFatherBirthDate;
            this.cardUserMatherBirthDate = cardUserMatherBirthDate;
            this.cardUserMatherName = cardUserMatherName;
            this.cardNocaisse = cardNocaisse;
            this.cardUserBirthDate= cardUserBirthDate;
            this.cardUserBrithPlace=cardUserBrithPlace;
        }

       

        public int getCardid() {
            return cardid;
        }

        public String getCardDateEtabishment() {
            return cardDateEtabishment;
        }

        public String getCardDateEpiration() {
            return cardDateEpiration;
        }

        public String getCardLocationEtabishment() {
            return cardLocationEtabishment;
        }

        public String getCardImatriculation() {
            return cardImatriculation;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public String getCardUserSex() {
            return cardUserSex;
        }

        public String getCardUserPhoto() {
            return cardUserPhoto;
        }

        public String getCardUserAdress() {
            return cardUserAdress;
        }

        public String getCardUserPofession() {
            return cardUserPofession;
        }

        public String getCardUserFatherName() {
            return cardUserFatherName;
        }

        public String getCardUserFatherBirthDate() {
            return cardUserFatherBirthDate;
        }

        public String getCardUserMatherBirthDate() {
            return cardUserMatherBirthDate;
        }

        public String getCardUserMatherName() {
            return cardUserMatherName;
        }

        public String getCardNocaisse() {
            return cardNocaisse;
        }

        public String getCardUserFname() {
            return cardUserFname;
        }

        public String getCardUserLname() {
            return cardUserLname;
        }

        public String getCardUserBirthDate() {
            return cardUserBirthDate;
        }

        public String getCardUserBrithPlace() {
            return cardUserBrithPlace;
        }
        
     }
     private static String SetCardDataInQuery(Card card){
        String insertData = "INSERT INTO card_tbl VALUES (";
        insertData+=card.cardid +" , '";
        insertData+=card.getCardUserFname() +"' , '";
        insertData+=card.getCardUserLname() +"' , '";
        insertData+=card.getCardDateEtabishment() +"' , '";
        insertData+=card.getCardDateEpiration() +"' , '";
        insertData+=card.getCardLocationEtabishment() +"' , '";
        insertData+=card.getCardImatriculation() +"' , '";
        insertData+=card.getCardNumber() +"' , '";
        insertData+=card.getCardUserSex() +"' , '";
        insertData+=card.getCardUserPhoto() +"' , '";
        insertData+=card.getCardUserAdress() +"' , '";
        insertData+=card.getCardUserPofession() +"' , '";
        insertData+=card.getCardUserFatherName() +"' , '";
        insertData+=card.getCardUserFatherBirthDate() +"' , '";
        insertData+=card.getCardUserMatherName() +"' , '";
        insertData+=card.getCardUserMatherBirthDate() +"' , '";
        insertData+=card.getCardNocaisse()+"'" ;
        insertData+= ")";
        return insertData; 
     }
     private int GetCurrentPosQueNum(String pos){
         if(!currentMapCount.containsKey("clientID_"+pos))
           return 0;  
         else return currentMapCount.get("clientID_"+pos);
     }
    private void IncreasePosQueNum(String pos){
         int posQueNum=0; 
        if(currentMapCount.containsKey("clientID_"+pos))
             posQueNum = currentMapCount.get("clientID_"+pos);
         currentMapCount.put("clientID_"+pos, posQueNum+1);  
    }
    private void DecreasePosQueNum(String pos){
        if(currentMapCount.containsKey("clientID_"+pos)){
            int posQueNum = currentMapCount.get("clientID_"+pos);
            currentMapCount.put("clientID_"+pos, posQueNum-1);
        }
        
    }
    
    private void AddCardTocurrent(String pos , Map<String ,Object> card){
        if(!curretcards.containsKey("clientID_"+pos)){
            System.out.println("clientID_"+pos);
            List<Map<String ,Object>> poscurretcards = new ArrayList<Map<String ,Object>>();
            poscurretcards.add(card);
            curretcards.put("clientID_"+pos, poscurretcards);
        }
        else {
            curretcards.get("clientID_"+pos).add(card);
        }
    }
    
}
