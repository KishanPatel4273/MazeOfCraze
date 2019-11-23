package Questions;

import java.util.ArrayList;

import Display.ButtonHandler;

public class QuestionHandler {
	
	private String answerPressed;
	private Question currentQuestion;
	private int currentIndex;
	private ArrayList<Integer> indexFix;
	
	public QuestionHandler() {
		indexFix = new ArrayList<Integer>();
		Question.Questions.clear();
		/*1*/	Question.Questions.add(new Question("What does FBLA stand for?", "Florida Board of Landscape Architecture", "Fitness Business of Latin America", "Future Business Leaders of America", "Federal Bills of Lading Act of 1916", 3, 1));

		/*2*/	Question.Questions.add(new Question("Why should students join FBLA?", "College Preparation", "Career Exploration", "Academic Competitions", "All of the above", 4, 1));

		/*3*/	Question.Questions.add(new Question("Which of the following best describes FBLA?", "The largest career student business organization in the world", "A national math club", "A national science club", "A career preperation club", 1, 1));

		/*4*/	Question.Questions.add(new Question("What are the benefits of FBLA?", "Discounts", "Scholarships", "Award and Recognition Programs", "All of the above", 4, 1));

		/*5*/	Question.Questions.add(new Question("When can you join your high school chapter of FBLA?", "Only freshman year", "Anytime", "Only sophomore year", "Only  junior year", 2, 1));

		/*6*/	Question.Questions.add(new Question("Who is the President of NJ FBLA?", "Aakash Desai", "Garett Koch", "Seharsh Baxy", "Stephanie Hu", 2, 2));

		/*7*/	Question.Questions.add(new Question("Who is the Parliamentarian of NJ FBLA?", "Aakash Desai", "Garett Koch", "Seharsh Baxy", "Stephanie Hu", 1, 2));

		/*8*/	Question.Questions.add(new Question("Who is the Secretary of NJ FBLA?", "Aakash Desai", "Garett Koch", "Seharsh Baxy", "Stephanie Hu", 3, 2));

		/*9*/	Question.Questions.add(new Question("Who is the Historian of NJ FBLA?", "Aakash Desai", "Garett Koch", "Seharsh Baxy", "Stephanie Hu", 4, 2));

		/*10*/	Question.Questions.add(new Question("Who is the NJ Southern Region VP?", "Aakash Desai", "Mubeen Momodu", "Seharsh Baxy", "Stephanie Hu", 2, 2));
			
		/*11*/	Question.Questions.add(new Question("What is the flagship publiction of FBLA called?", "Future Business Leaders of America", "Elevate your future", "Tomorrow's Business Leaders", "Business Leaders of the Future", 3, 3));

		/*12*/	Question.Questions.add(new Question("Which of the following is not a competitive event?", "Public Speaking", "Digital Video Production", "Computer Game and Simulation Programming", "Business Management", 4, 3));

		/*13*/	Question.Questions.add(new Question("What concepts are covered through the Business Achievement Awards?", "Service", "Education", "Progress", "All of the above", 4, 3));

		/*14*/	Question.Questions.add(new Question("About how many competitive events does FBLA have?", "30", "50", "70", "90", 3, 3));

		/*15*/	Question.Questions.add(new Question("Where is the NJ State Leadership Conference located?", "Edison", "Trenton", "Atlantic City", "New Brunswick", 3, 3));
			
		/*16*/	Question.Questions.add(new Question("How many regions of FBLA-PBL are there?", "2", "3", "4", "5", 4, 4));

		/*17*/	Question.Questions.add(new Question("Where is NLC taking place in 2019", "San Antonio", "New York City", "Baltimore", "Chicago", 1, 4));

		/*18*/	Question.Questions.add(new Question("What is the college division of FBLA called?", "FBLA", "Professional Division", "PBL", "FBLA-Middle Level", 3, 4));

		/*19*/	Question.Questions.add(new Question("About how many members does FBLA have in all of its divisions?", "50,000", "75,000", "125,000", "225,000", 4, 4));

		/*20*/	Question.Questions.add(new Question("How many divisions of FBLA are there?", "1", "2", "3", "4", 4, 4));
			
		/*21*/	Question.Questions.add(new Question("About how many career clusters do the FBLA competitive events span?", "5", "10", "20", "30", 2, 5));

		/*22*/	Question.Questions.add(new Question("Who is the Webmaster of NJ FBLA", "Arnav Tolat", "Harvey Wang", "Mubeen Momodu", "Kevin Kennedy", 2, 5));

		/*23*/	Question.Questions.add(new Question("Who is the founder of FBLA?", "Henry L. Williams", "John R. Smith", "George Washington", "Hamden L. Forkner", 4, 5));

		/*24*/	Question.Questions.add(new Question("When was the first high school chapter of FBLA created?", "1942", "1943", "1941","1940", 1, 5));

		/*25*/	Question.Questions.add(new Question("Where was the first high school chapter of FBLA created?", "New Jersey", "Tennessee", "Florida", "New York", 2, 5));
		
		answerPressed = "-1";
	}
	
	//see if answer is pressed
	public void tick(ButtonHandler buttonHandler) {
		if(!buttonHandler.getAnswerPressed().equals("-1")) {
			setAnswerPressed(buttonHandler.getAnswerPressed());
		}
	}
	
	public void setAnswerPressed(String answerPressed) {
		this.answerPressed = answerPressed;
	}
	
	public String getAnswerPressed() {
		return answerPressed;
	}
	
	//gets new question
	public void askQuestion(ButtonHandler buttonHandler, int d) {
		currentQuestion = getQuestion(d);
		buttonHandler.setTextLabel(currentQuestion.getLabelText());
		buttonHandler.turnOnQuestion();
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	//removes questions asked
	public void remove(int index) {
		Question.Questions.remove(index);
		for(int i = index; i < Question.Questions.size(); i++) {
			Question.Questions.get(i).setIndex(Question.Questions.get(i).getIndex() - 1);
		}
	}
	
	//removes questions asked
	public void remove() {
		Question.Questions.get(currentIndex).use();
	}
	
	//generates new question
	public Question getQuestion(int difficulty) {
		int index = (int) (Math.random() * Question.Questions.size());
		while(Question.Questions.get(index).getDifficulty() != difficulty || Question.Questions.get(index).getUsed() != false) {
			 index = (int) (Math.random() * Question.Questions.size());
		}
		currentIndex = index;
		//System.out.println(index + "Size" + Question.Questions.size());
		return Question.Questions.get(index);
	}
	
}
