import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JPanel {

    private static SortingAlgorithms sortAlgos = new SortingAlgorithms();

    // Initializing needed variables
    private static final int ARRAY_SIZE = 850;
    private static int[] arr = new int[ARRAY_SIZE];
    private static int high = 700, low = 150;
    private static Random rand = new Random();

    // JFrame components
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 770;
    private static final int TIMER_DELAY = 20;
    private static JLabel isSortedLabel = new JLabel();
    private static Color lineColor = Color.BLACK;
    private static String[] sortingAlgos = {"Bubble Sort", "Merge Sort", "Quick Sort",
                                            "Selection Sort", "Insertion Sort", "Heap Sort"};

    // Randomize array button and label
    private static JButton randomizeButton = new JButton("Randomize");
    private static JLabel randomizeJLabel = new JLabel("Randomize:");

    // Sort array button and combo box
    private static JComboBox<String> sortComboBox = new JComboBox<String>(sortingAlgos);
    private static JButton sortButton = new JButton("Sort");

    // Constructor
    public Main() {
        randomize();
        timer.start();

        // Randomize label and button bounds
        randomizeJLabel.setBounds(40, 25, 180, 25);
        randomizeButton.setBounds(150, 25, 120, 25);

        // Sort combo box and button bounds
        sortComboBox.setBounds(40, 65, 180, 25);
        sortButton.setBounds(240, 65, 120, 25);

        isSortedLabel.setBounds(400, 65, 180, 25);

        // Button listeners
        randomizeButton.addActionListener((ActionListener) new RandomizeListener());
        sortButton.addActionListener((ActionListener) new SortListener());
    }

    // Main
    public static void main(String[] args) {
        Main gui = new Main();
        JFrame frame = new JFrame("Sorting Visualizer");

        // Adding JFrame contents to the frame
        frame.add(randomizeButton);
        frame.add(randomizeJLabel);
        frame.add(sortButton);
        frame.add(sortComboBox);
        frame.add(isSortedLabel);
        frame.add(gui);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    // Randomizes the array
    private class RandomizeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            randomize();
        }
    }

    // Sorts the array
    private class SortListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sort();
        }
    }

    // Paint Component
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(lineColor);

        int max = 850;
        int arrIndex = 0;
        for (int i = 50; i < max; i++) {
            g.drawLine(i, 150, i, arr[arrIndex]);
            arrIndex++;
        }
    }

    // Calls the sorting algorithms
    private void sort() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                sortButton.setEnabled(false);
                randomizeButton.setEnabled(false);

                // Checks if array is sorted before sorting it
                if (isSorted(arr)) {
                    isSortedLabel.setText("Array is already sorted!");
                    isSortedLabel.setForeground(Color.red);
                    isSortedLabel.setVisible(true);
                }
                else {
                    isSortedLabel.setVisible(false);
                    int selectedValue = sortComboBox.getSelectedIndex();
                    switch (selectedValue) {
                        case 0:
                            sortAlgos.bubbleSort(arr);
                            break;

                        case 1:
                            sortAlgos.mergeSort(arr, 0, ARRAY_SIZE - 1);
                            break;

                        case 2:
                            sortAlgos.quickSort(arr, 0, ARRAY_SIZE - 1);
                            break;

                    case 3:
                        sortAlgos.selectionSort(arr);
                        break;

                    case 4:
                        sortAlgos.insertionSort(arr);
                        break;

                    case 5:
                        sortAlgos.heapSort(arr);
                        break;
                    }
                }

                sortButton.setEnabled(true);
                randomizeButton.setEnabled(true);
                return null;
            }

        };
        worker.execute();
    }

    // Randomizes the array
    private void randomize() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < ARRAY_SIZE; i++) {
                    int randomNum = rand.nextInt(high - low) + low;
                    arr[i] = randomNum;
                }
                return null;
            }

        };
        worker.execute();
    }

    // Checks if the array is sorted
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            if (arr[i] > arr[i + 1])
                return false;
        }
        return true;
    }

    // Swing timer - repaints the frame after a delay
    javax.swing.Timer timer;

    {
        timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(0, 150, 900, 770);
            }
        });
    }

}
