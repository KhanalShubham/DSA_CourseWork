import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Question_7routeOptimization extends JFrame {

    private Map<String, Point> cityPositions;
    private Map<String, Integer> cityIndexMap;
    private int[][] distances;

    private String startCity;
    private String endCity;

    private List<Integer> shortestPath;

    public Question_7routeOptimization() {
        setTitle("Unique Route Optimization for Delivery Service");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize cities and distances
        initializeCitiesAndDistances();

        // Main layout
        setLayout(new BorderLayout(30, 30));

        // Graph panel with custom design
        CustomGraphPanel graphPanel = new CustomGraphPanel();
        graphPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(graphPanel, BorderLayout.CENTER);

        // Side panel for displaying shortest path
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new GridBagLayout());
        pathPanel.setBackground(new Color(240, 240, 240));  // Light gray background
        pathPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 3));  // Dark blue border
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel pathLabel = new JLabel("Shortest Path will be displayed here");
        pathLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        pathLabel.setForeground(new Color(0, 51, 102));  // Dark blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pathPanel.add(pathLabel, gbc);

        JButton clearButton = new JButton("Clear Path");
        clearButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        clearButton.setBackground(new Color(255, 102, 102));  // Light red color
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createLineBorder(new Color(204, 0, 0)));  // Darker red border
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> {
            pathLabel.setText("Shortest Path will be displayed here");
            graphPanel.repaint();
        });
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        pathPanel.add(clearButton, gbc);

        JButton optimizeButton = new JButton("Optimize Route");
        optimizeButton.setFont(new Font("Verdana", Font.BOLD, 16));
        optimizeButton.setBackground(new Color(0, 153, 51));  // Green color
        optimizeButton.setForeground(Color.WHITE);
        optimizeButton.setFocusPainted(false);
        optimizeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 0)));  // Darker green border
        optimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optimizeButton.addActionListener(e -> {
            if (startCity != null && endCity != null && !startCity.equals(endCity)) {
                findShortestPath(startCity, endCity);
                pathLabel.setText("Shortest Path: " + shortestPathToString());
                graphPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Please select different start and end cities.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        pathPanel.add(optimizeButton, gbc);

        add(pathPanel, BorderLayout.EAST);

        // Input panel with custom styling and interactive elements
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(245, 245, 245));  // Light gray background
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Input", TitledBorder.CENTER, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 16), new Color(0, 51, 102)));  // Dark blue border and title

        JLabel startLabel = new JLabel("Start City:");
        startLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        startLabel.setForeground(new Color(0, 51, 102));  // Dark blue color
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(10, 10, 10, 10);

        gbcInput.gridx = 0;
        gbcInput.gridy = 0;
        inputPanel.add(startLabel, gbcInput);

        JComboBox<String> startComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        startComboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        startComboBox.setToolTipText("Select the starting city");
        startComboBox.addActionListener(e -> {
            startCity = (String) startComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        gbcInput.gridx = 1;
        inputPanel.add(startComboBox, gbcInput);

        JLabel endLabel = new JLabel("End City:");
        endLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        endLabel.setForeground(new Color(0, 51, 102));  // Dark blue color
        gbcInput.gridy = 1;
        gbcInput.gridx = 0;
        inputPanel.add(endLabel, gbcInput);

        JComboBox<String> endComboBox = new JComboBox<>(cityIndexMap.keySet().toArray(new String[0]));
        endComboBox.setFont(new Font("Verdana", Font.PLAIN, 14));
        endComboBox.setToolTipText("Select the ending city");
        endComboBox.addActionListener(e -> {
            endCity = (String) endComboBox.getSelectedItem();
            graphPanel.repaint();
        });
        gbcInput.gridx = 1;
        inputPanel.add(endComboBox, gbcInput);

        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeCitiesAndDistances() {
        // Initialize city positions (for graphical representation)
        cityPositions = new HashMap<>();
        cityPositions.put("CityA", new Point(300, 150));
        cityPositions.put("CityB", new Point(100, 400));
        cityPositions.put("CityC", new Point(500, 150));
        cityPositions.put("CityD", new Point(300, 600));
        cityPositions.put("CityE", new Point(700, 400));
        cityPositions.put("CityF", new Point(900, 200));

        // Initialize cities and their indices
        String[] cities = {"CityA", "CityB", "CityC", "CityD", "CityE", "CityF"};
        cityIndexMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            cityIndexMap.put(cities[i], i);
        }

        // Initialize distances (adjacency matrix)
        distances = new int[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0;
        }

        // Add connections between cities
        addConnection("CityA", "CityB", 250);
        addConnection("CityB", "CityD", 100);
        addConnection("CityD", "CityF", 200);
        addConnection("CityF", "CityE", 100);
        addConnection("CityC", "CityF", 120);
        addConnection("CityC", "CityB", 250);
    }

    private void addConnection(String city1, String city2, int distance) {
        int index1 = cityIndexMap.get(city1);
        int index2 = cityIndexMap.get(city2);
        distances[index1][index2] = distance;
        distances[index2][index1] = distance;
    }

    private void findShortestPath(String startCity, String endCity) {
        int startIndex = cityIndexMap.get(startCity);
        int endIndex = cityIndexMap.get(endCity);
        shortestPath = dijkstra(startIndex, endIndex);
    }

    private List<Integer> dijkstra(int start, int end) {
        int numCities = distances.length;
        int[] minDistances = new int[numCities];
        boolean[] visited = new boolean[numCities];
        int[] previous = new int[numCities];

        Arrays.fill(minDistances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        minDistances[start] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(i -> minDistances[i]));
        queue.add(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;

            for (int v = 0; v < numCities; v++) {
                if (!visited[v] && distances[u][v] != Integer.MAX_VALUE) {
                    int newDist = minDistances[u] + distances[u][v];
                    if (newDist < minDistances[v]) {
                        minDistances[v] = newDist;
                        previous[v] = u;
                        queue.add(v);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private String shortestPathToString() {
        if (shortestPath == null || shortestPath.isEmpty()) {
            return "No path found";
        }
        StringBuilder sb = new StringBuilder();
        for (int i : shortestPath) {
            sb.append(getCityName(i)).append(" ");
        }
        return sb.toString().trim();
    }

    private String getCityName(int index) {
        for (Map.Entry<String, Integer> entry : cityIndexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    private class CustomGraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCitiesAndConnections(g);
            if (shortestPath != null) {
                drawShortestPath(g);
            }
        }

        private void drawCitiesAndConnections(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));

            for (int i = 0; i < distances.length; i++) {
                Point city1 = cityPositions.get(getCityName(i));
                for (int j = i + 1; j < distances.length; j++) {
                    if (distances[i][j] != Integer.MAX_VALUE) {
                        Point city2 = cityPositions.get(getCityName(j));
                        g2d.setColor(new Color(180, 180, 180, 150));  // Semi-transparent light gray
                        g2d.drawLine(city1.x, city1.y, city2.x, city2.y);
                    }
                }
            }

            for (int i = 0; i < distances.length; i++) {
                Point city = cityPositions.get(getCityName(i));
                g2d.setColor(new Color(0, 153, 204));  // Teal color
                g2d.fillOval(city.x - 8, city.y - 8, 16, 16);
                g2d.setColor(Color.WHITE);
                g2d.drawString(getCityName(i), city.x + 12, city.y);
            }
        }

        private void drawShortestPath(Graphics g) {
            if (shortestPath.size() < 2) {
                return;
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(new Color(255, 69, 0));  // Red-Orange color

            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Point city1 = cityPositions.get(getCityName(shortestPath.get(i)));
                Point city2 = cityPositions.get(getCityName(shortestPath.get(i + 1)));
                g2d.drawLine(city1.x, city1.y, city2.x, city2.y);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Question_7routeOptimization());
    }
}
