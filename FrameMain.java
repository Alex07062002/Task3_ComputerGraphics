package ru.vsu.cs.course1;

import ru.vsu.cs.util.JTableUtils;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable coordinates;
    private JButton paint;
    private JTextField xField;
    private JTextField yField;
    private JTextField scaleField;
    private JButton clearBut;
    private JLabel scaleMistLabel;
    private JPanel figures;
    private JTextField CornerField;
    private JButton MatrixRotatebutton;
    private JButton MatrixScalebutton;
    private JTable parameters;
    private JButton TranslateMatrixButton;
    private JButton ClearMatrixButton;
    private JTextField NumberCoordinates;
    private JTable Lines;
    private JCheckBox поворотПоОсиZCheckBox;
    private JCheckBox поворотПоОсиYCheckBox;
    private JCheckBox поворотПоОсиXCheckBox;


    private final int imageWidth = 600;
    private final int imageHeight = 300;
    private final int stepH = 150;
    private final int stepW = 300;
    private final int scale = 10;

    Figure fig = new Figure(new ArrayList<>());
    Figure addit = new Figure(new ArrayList<>());
    int tableSize;


    private void drawFigure(Graphics g, Figure f, int[][] line) {
        g.setColor(Color.MAGENTA);
        for (int i = 0; i<line.length;i++){
            for (int j = 0; j<line[0].length;j++){
                if (line[i][j] != 0){
                    g.drawLine((int)f.getCoordinates().get(i).getX()*scale + stepW, (int)f.getCoordinates().get(i).getY()*scale + stepH, (int)f.getCoordinates().get(j).getX()*scale + stepW, (int)f.getCoordinates().get(j).getY()*scale + stepH);

                }
            }
        }
    }

    private void clearFigures (Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth*10, imageHeight*10);
        g.setColor(Color.BLACK);
        g.drawLine(imageWidth / 2, 0, imageWidth / 2, imageHeight);
        g.drawLine(0, imageHeight / 2, imageWidth, imageHeight / 2);
    }

    public FrameMain() {
        this.setTitle("График");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        NumberCoordinates.addActionListener(e -> {
                    tableSize = Integer.parseInt(NumberCoordinates.getText());
                    int[][] arr = new int[3][tableSize];
            for (int[] ints : arr) {
                Arrays.fill(ints, 0);
            }
            JTableUtils.initJTableForArray(coordinates, 30, false, false, false, false);
            coordinates.setRowHeight(30);
            JTableUtils.writeArrayToJTable(coordinates, arr);
            int [][]line = new int[tableSize][tableSize];
            for (int[] ints : line) {
                Arrays.fill(ints, 0);
            }
            JTableUtils.initJTableForArray(Lines, 30, false, false, false, false);
            Lines.setRowHeight(30);
            JTableUtils.writeArrayToJTable(Lines, line);
                });


        double [][] atributs = new double[4][4];
        JTableUtils.initJTableForArray(parameters, 100, false, false, false, false);
        parameters.setRowHeight(50);
        atributs[3][3]=1;
        JTableUtils.writeArrayToJTable(parameters, atributs);
        this.pack();

        JPanel myPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(imageWidth / 2, 0, imageWidth / 2, imageHeight);
                g.drawLine(0, imageHeight / 2, imageWidth, imageHeight / 2);
            }
        };
        figures.setLayout(new BorderLayout());
        myPanel.setPreferredSize(new Dimension(imageWidth,imageHeight));
        figures.add(myPanel, BorderLayout.CENTER);

        paint.addActionListener(e -> {
            clearFigures(figures.getGraphics());
            try {
                int [][] arr = JTableUtils.readIntMatrixFromJTable(coordinates);
                int [][] line = JTableUtils.readIntMatrixFromJTable(Lines);
                if (arr != null) {
                    fig = Logic.saveFromArrayToFigure(arr);
                    Graphics g = figures.getGraphics();
                    assert line != null;
                    drawFigure(g, fig,line);
                } else {
                    fig = null;
                }
            } catch (ParseException ignored) {
            }
        });

        clearBut.addActionListener(e -> {
            int [][] arr =new int[3][tableSize];
            JTableUtils.writeArrayToJTable(coordinates,arr);
            int[][]line = new int[tableSize][tableSize];
            JTableUtils.writeArrayToJTable(Lines,line);
            xField.setText("");
            yField.setText("");
            scaleField.setText("");
            scaleMistLabel.setText("");
            clearFigures(figures.getGraphics());
            fig = new Figure(new ArrayList<>());
            addit = new Figure(new ArrayList<>());
        });
        MatrixRotatebutton.addActionListener(e -> {
            clearFigures(figures.getGraphics());
            double[][]matrix;
            if (!CornerField.getText().trim().isEmpty()) {
                matrix = atributs;
                addit = fig.rotateMatrix(matrix);
                int [][] line = new int[0][];
                try {
                    line = JTableUtils.readIntMatrixFromJTable(Lines);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                assert line != null;
                drawFigure(figures.getGraphics(), addit,line);
            }else {
                try {
                    matrix = JTableUtils.readDoubleMatrixFromJTable(parameters);
                if (matrix != null) {
                    addit = fig.rotateMatrix(matrix);
                    int [][] line = new int[0][];
                    try {
                        line = JTableUtils.readIntMatrixFromJTable(Lines);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    assert line != null;
                    drawFigure(figures.getGraphics(), addit,line);
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            }
        });

             CornerField.addActionListener(e -> {
                 if (поворотПоОсиXCheckBox.isSelected()) {
                     for (int i = 0; i<atributs.length;i++){
                         for (int j = 0; j<atributs[0].length;j++){
                             if ((i == 3) && (j == 3)){
                                 atributs[i][j] = 1;
                             } else {
                                 atributs[i][j] = 0;
                             }
                         }
                     }
                     try {
                         atributs[0][0] = 1;
                         atributs[1][1] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                         atributs[1][2] = -Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                         atributs[2][1] = Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                         atributs[2][2] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                         JTableUtils.writeArrayToJTable(parameters, atributs);

                     } catch (Exception exc) {
                         ru.vsu.cs.util.SwingUtils.showErrorMessageBox(exc);
                     }
                 }else if (поворотПоОсиYCheckBox.isSelected()){
                     for (int i = 0; i<atributs.length;i++){
                         for (int j = 0; j<atributs[0].length;j++){
                             if ((i == 3) && (j == 3)){
                                 atributs[i][j] = 1;
                             } else {
                                 atributs[i][j] = 0;
                             }
                         }
                     }
                     try {
                     atributs[1][1] = 1;
                     atributs[0][0] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                     atributs[2][0] = -Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                     atributs[0][2] = Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                     atributs[2][2] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                         JTableUtils.writeArrayToJTable(parameters, atributs);

                     } catch (Exception exc) {
                         ru.vsu.cs.util.SwingUtils.showErrorMessageBox(exc);
                     }
                 } else if (поворотПоОсиZCheckBox.isSelected()){
                     for (int i = 0; i<atributs.length;i++){
                         for (int j = 0; j<atributs[0].length;j++){
                             if ((i == 3) && (j == 3)){
                                 atributs[i][j] = 1;
                             } else {
                                 atributs[i][j] = 0;
                             }
                         }
                     }
                         try{
                             atributs[2][2] = 1;
                             atributs[0][0] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                             atributs[0][1] = -Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                             atributs[1][0] = Math.sin((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                             atributs[1][1] = Math.cos((Integer.parseInt(CornerField.getText()) * Math.PI) / 180);
                             JTableUtils.writeArrayToJTable(parameters, atributs);

                         }catch (Exception exc) {
                             ru.vsu.cs.util.SwingUtils.showErrorMessageBox(exc);
                         }
                     }
        });
        MatrixScalebutton.addActionListener(e -> {
            clearFigures(figures.getGraphics());
            try {
                double[][]matrix = JTableUtils.readDoubleMatrixFromJTable(parameters);
                if (matrix != null) {
                    addit = fig.scaleMatrix(matrix);
                    int [][] line = new int[0][];
                    try {
                        line = JTableUtils.readIntMatrixFromJTable(Lines);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    assert line != null;
                    drawFigure(figures.getGraphics(), addit,line);
                }
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
        });
        TranslateMatrixButton.addActionListener(e -> {
            clearFigures(figures.getGraphics());
            try {
                double[][]matrix = JTableUtils.readDoubleMatrixFromJTable(parameters);
                if (matrix != null) {
                    addit = fig.translateMatrix(matrix);
                    int [][] line = new int[0][];
                    try {
                        line = JTableUtils.readIntMatrixFromJTable(Lines);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    assert line != null;
                    drawFigure(figures.getGraphics(), addit,line);
                }
            } catch (ParseException exception) {
                exception.printStackTrace();
            }

        });
        ClearMatrixButton.addActionListener(e -> {
            double [][] atributs1 = new double[4][4];
            JTableUtils.initJTableForArray(parameters, 100, false, false, false, false);
            parameters.setRowHeight(50);
            atributs1[3][3]=1;
            JTableUtils.writeArrayToJTable(parameters, atributs1);
            CornerField.setText(null);
        });
    }
}
