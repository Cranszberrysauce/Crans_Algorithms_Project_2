package crans_project_2;

import java.util.Arrays;
import java.util.List;

/******************************************************
***  FiniteAutomata
***  Nick Crans
******************************************************
*** Holds the transition table, current state, and 
*** performs nescessary operations during the program.
******************************************************
*** 02/25/2019: Class created
*** 02/25/2019: Created and finished methods and functions
******************************************************/
public class FiniteAutomata 
{
    private int currentState;
    private int numberOfStates;
    private char[] alphabet;
    private int[][] changeTable;           //How machine knows when to change state
    private int startState;
    private List<Integer> acceptingStates;
    
    public FiniteAutomata(int states, String characters, int[][] transitions, int start, 
                            List<Integer> accept)
    {
        currentState = start;
        numberOfStates = states;
        alphabet = new char[characters.length()];
        characters = sortAlphabet(characters);
        for(int i = 0; i < characters.length(); i++)
            alphabet[i] = characters.charAt(i);
        changeTable = transitions;
        startState = start;
        acceptingStates = accept;
    }
    
    /******************************************************
    ‘***  canStop
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Machine needs to know when to stop. Stopping conditions
    ‘*** are: character not in alphabet is in string, no more
    ‘*** characters in string and machine has stopped in
    ‘*** an accepting state.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public boolean canStop(String testWord)
    {
        boolean isAccepted = false;
        
        for(int character = 0; character < testWord.length(); character++)
        {
            isAccepted = inAlphabet(testWord.charAt(character));
            
            if(!isAccepted)
                return isAccepted;
            else
                stateChange(testWord.charAt(character));
        }
        
        isAccepted = inAcceptingSTate();
            
        return isAccepted;
    }
    
    /******************************************************
    ‘***  resetRules
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed if user wants to create a new machine.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public void resetRules()
    {
        if(changeTable != null)
            changeTable = null;
    }
    
    /******************************************************
    ‘***  resetState
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed if multiple strings are entered for testing
    ‘*** machine.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public void resetState()
    {
        currentState = startState;
    }
    
    /******************************************************
    ‘***  inAcceptingSTate
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to prove machine is in accepting state.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    private boolean inAcceptingSTate()
    {
        int acceptState;
        boolean accept = true;
        
        for(int i = 0; i < acceptingStates.size(); i++)
        {
            acceptState = acceptingStates.get(i);
            
            if(currentState == acceptState)
                return accept;
            else
                accept = false;
        }
        
        return accept;
    }
    
    /******************************************************
    ‘***  inAlphabet
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Used if input for any character entered is in the
    ‘*** alphabet entered when machine was created.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public boolean inAlphabet(char testCharcter)
    {
        boolean isInAlphabet = false;
        String strAlphabet = "";
        int charIndex;
        
        for(int i = 0; i < alphabet.length; i++)
            strAlphabet += alphabet[i];
            
        charIndex = strAlphabet.indexOf(testCharcter);
        
        if(charIndex == -1)
            return isInAlphabet;
        else
            isInAlphabet = true;

        return isInAlphabet;
    }
    
    /******************************************************
    ‘***  stateChange
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to change the state the machine is in.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    private void stateChange(char currentChar)
    {
        int oldState = currentState;
        int newState = getCharacterIndex(Character.toString(currentChar)) + 1;
                
        currentState = changeTable[oldState][newState];
    }
    
    /******************************************************
    ‘***  fillTransTable
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to create the transition rules for the machine.
    ‘*** The table created allows the machine to know which 
    ‘*** state to change to when consuming characters in the
    ‘*** string.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public void fillTransTable(int state, String character, int nextState)
    {
        int nextCharacter = getCharacterIndex(character) + 1;
        int numStates = numberOfStates + 1;
        int numAlphabet = alphabet.length + 1;
        
        if(changeTable == null)
        {
            changeTable = new int[numStates][numAlphabet];
            if(nextCharacter != -1)
                changeTable[state][nextCharacter] = nextState; 
        }
        else if(nextCharacter != -1)
            changeTable[state][nextCharacter] = nextState; 
    }
    
    /******************************************************
    ‘***  getCharacterIndex
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to know which character in the alphabet was 
    ‘*** entered. Ex. Alphabet: abc, [a = 1, b = 2, c = 3].
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    private int getCharacterIndex(String character)
    {
        char charToFind = character.charAt(0);
        int characterIndex = recGetCharIndex(alphabet, charToFind, 0, alphabet.length -1);
        
        return characterIndex;
    }
    
    /******************************************************
    ‘***  recGetCharIndex
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to know which character in the alphabet was 
    ‘*** entered. Ex. Alphabet: abc, [a = 1, b = 2, c = 3].
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    private int recGetCharIndex(char[] alphabet, char charToFind, int left, int right)
    {
        if(alphabet == null){
            return -1;
        }
        if(right >= left)
        {
            int pivot = (left + right) / 2;
        
            if(charToFind == alphabet[pivot])
                return pivot;
            else if(charToFind > alphabet[pivot])
                return recGetCharIndex(alphabet, charToFind, pivot + 1, right);
            else if(charToFind < alphabet[pivot])
                return recGetCharIndex(alphabet, charToFind, left, pivot - 1);           
        }
        
        return -1;
    }
    
    /******************************************************
    ‘***  sortAlphabet
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** When the alphabet is sorted from lowest to highest,
    ‘*** the index of the alphabet array can be used to know
    ‘*** which state to change to.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    private String sortAlphabet(String unsortedAlphabet)
    {
        String sortedAlphabet = "";
        char[] charHolder = new char[unsortedAlphabet.length()];
        
        for(int character = 0; character < unsortedAlphabet.length(); character++)
            charHolder[character] = unsortedAlphabet.charAt(character);
        
        Arrays.sort(charHolder);
        
        for(int character = 0; character < unsortedAlphabet.length(); character++)
            sortedAlphabet += charHolder[character];
        
        return sortedAlphabet;
    }
    
    /******************************************************
    ‘***  getNumberOfStates
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to test if any state entered is in the correct
    ‘*** range of states. Ex. 5 States, 6 entered for any
    ‘*** state input, reject input due to value being higher
    ‘*** than number of states.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public int getNumberOfStates()
    {
        return numberOfStates;
    }
    
    /******************************************************
    ‘***  tableIsEmpty
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to ensure the user does not test a string
    ‘*** if transition table is empty or incomplete.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    public boolean tableIsEmpty()
    {
        boolean isEmpty = true;
        
        for(int state = 1; state < changeTable.length; state++)
        {
            for(int newState = 1; newState < changeTable[state].length; newState++)
            {
                if(changeTable[state][newState] == 0)
                    return isEmpty;                    
            }
        }
        isEmpty = false;
        
        return isEmpty;
    }
    
    public String showTransitions(char currChar)
    {
        String transitions;
        String tempChar = Character.toString(currChar);
        int currentChar, oldState;
        boolean inAlph = false;
        
        inAlph = inAlphabet(currChar);
        
        if(inAlph)
        {
            transitions = "\nCurrent State: " + currentState + "\n";
            oldState = currentState; 
            currentChar = getCharacterIndex(tempChar);
            stateChange(currChar);
            tempChar = Character.toString(alphabet[currentChar]);
            transitions += "Next character: " + tempChar + "\n";
            transitions += "Transition to State: " + changeTable[oldState][currentChar + 1] + "\n";
            
            return transitions;
        }

        return "";
    }
    
    public String displayRules()
    {
        String rulesTable = "";
        
        for(int i = 0; i < changeTable.length; i++)
        {
            for(int j = 1; j < changeTable[i].length; j++)
                rulesTable += (Integer.toString(i) + ", " + changeTable[i][j] + "\n");
        }
        
        return rulesTable;
    }
    
    /******************************************************
    ‘***  toString
    ‘***  Nick Crans
    ‘******************************************************
    ‘*** Needed to show the user the machine that has been
    ‘*** created.
    ‘******************************************************
    ‘*** 09/27/2019
    ‘******************************************************/
    @Override
    public String toString()
    {
        String machineDisplay;
        String acceptState;
        int alphabetSize = alphabet.length;
        int numAcceptSt = acceptingStates.size();
        
        machineDisplay = "\nNumber of states: " + numberOfStates + "\n";
        
        for(int currChar = 0; currChar < alphabetSize; currChar++)
        {
            if(currChar == 0 && currChar == alphabetSize - 1)
                machineDisplay += ("Alphabet: " + alphabet[currChar] + "\n");
            else if(currChar == 0)
                machineDisplay += ("Alphabet: " + alphabet[currChar] + ", ");
            else if(currChar == alphabetSize - 1)
                machineDisplay += (alphabet[currChar] + "\n");
            else
                machineDisplay += (alphabet[currChar] + ", ");
        }
        
        machineDisplay += "Start state: " + currentState + "\n";
        
        for(int state = 0; state < numAcceptSt; state++)
        {
            if(state == 0 && state == numAcceptSt - 1)
            {
                acceptState = Integer.toString(acceptingStates.get(state));
                machineDisplay += ("Accepting states: " + acceptState + "\n");
            }  
            else if(state == 0)
            {
                acceptState = Integer.toString(acceptingStates.get(state));
                machineDisplay += ("Accepting states: " + acceptState + ", ");
            }    
            else if(state == numAcceptSt - 1)
            {
                acceptState = Integer.toString(acceptingStates.get(state));
                machineDisplay += (acceptState + "\n");
            }    
            else
            {
                acceptState = Integer.toString(acceptingStates.get(state));
                machineDisplay += (acceptState + ", ");
            }
        }
        
        return machineDisplay;
    }
}
/*
    private int numberOfStates;
    private char[] alphabet;
    private Transition changeState;
    private int startState;
    private List<Integer> acceptingStates;
    private List<State> states;
    private int currentState;
    
    public FiniteAutomata(int states, String characters, String[][] transitions, int start, 
                            List<Integer> accept)
    {
        int alphabetSize = characters.length();
        numberOfStates = states;
        alphabet = new char[alphabetSize];
        for(int i = 0; i < alphabetSize; i++)
            alphabet[i] = characters.charAt(i);
        changeState = new Transition(transitions);
        startState = start;
        acceptingStates = accept;
        states = new ArrayList();
    }
    
    public int getNumberOfStates()
    {
        return numberOfStates;
    }
    
    public char[] getAlphabet()
    {
        return alphabet;
    }
    
    public int getCurrentState()
    {
        return currentState;
    }
    
    public void setNumberOfStates(int states)
    {
        numberOfStates = states;
    }
    
    public void setNewAlphabet(String alphabet)
    {
        int alphabetSize = alphabet.length();
        char[] newAlphabet = new char[alphabet.length()];
        
        for(int i = 0; i < alphabetSize; i++)
            newAlphabet[i] = alphabet.charAt(i);
        
        this.alphabet = newAlphabet;
    }
    /*
    public void setState(int state, char character, boolean isAccepting)
    {
        currentState = new State(state, character, isAccepting);
    }
    
    public void setTable()
    {
        State transState = new State(1, "", 1, false);
        for(int currentState = 0; currentState < numberOfStates; currentState++)
        {
            transState.setCurrentState(currentState + 1);
            
            states[currentState] = transState;
        }
        
        for(int nextChar = 0; nextChar < alphabet.length; nextChar++)
        {
                states[currentState].setCharacter(Character.toString(alphabet[nextChar]));
        }
    }
    
    public void setTransitions(int state, String character, int nextState)
    {
        
        changeState.setRules(state, character, nextState);
    }
    
    public int getAlphabetSize()
    {
        return alphabet.length;
    }
    
    public int getCurrentCharacter(char charNeeded)
    {
        int charNeededIndex = -1;
        for(int currChar = 0; currChar < alphabet.length; currChar++)
        {
            if(alphabet[currChar] == charNeeded)
                charNeededIndex = currChar;
        }
        return charNeededIndex;
    }
    
    public char getCharacter(int charIndex)
    {
        return alphabet[charIndex];
    }
    
    @Override
    public String toString()
    {
        String machineDisplay;
        
        machineDisplay = "Number of states: " + numberOfStates + "\n";
        
        for(int currChar = 0; currChar < alphabet.length; currChar++)
        {
            if(currChar == 0 && currChar == alphabet.length - 1)
                machineDisplay += ("Alphabet: " + alphabet[currChar] + "\n");
            else if(currChar == 0)
                machineDisplay += ("Alphabet: " + alphabet[currChar] + ", ");
            else if(currChar == alphabet.length - 1)
                machineDisplay += (alphabet[currChar] + "\n");
            else
                machineDisplay += (alphabet[currChar] + ", ");
        }
        
        machineDisplay += ("Start state: " + startState + "\n");
        
        for(int acceptState = 0; acceptState < acceptingStates.size(); acceptState++)
        {
            if(acceptState == 0 && acceptState == acceptingStates.size() - 1)
                machineDisplay += ("Accepting state(s): " + acceptingStates.get(acceptState) + "\n");
            else if(acceptState == 0)
                machineDisplay += ("Accepting state(s): " + acceptingStates.get(acceptState) + ", ");
            else if(acceptState == acceptingStates.size() - 1)
                machineDisplay += (acceptingStates.get(acceptState) + "\n");
            else
                machineDisplay += (acceptingStates.get(acceptState) + ", ");
        }
        
        return machineDisplay;
    }
    
    public String displayRules()
    {
        String rules = "";
        
        for(int state = 0; state < numberOfStates; state++)
        {
            for(int transition = 1; transition < alphabet.length; transition++ )
            {
                rules += "---------------------------------------------------------------\n";
                rules += state + ", " + alphabet[transition] + ", " + changeState.getState(state, transition) + "\n";
            }
        }
        
        return rules;
    }
    */