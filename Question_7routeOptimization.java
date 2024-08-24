import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Question_7routeOptimization extends JFrame {

    private Map<String, Point> cityPositions;
    private Map<String, Integer> cityIndexMap;
    private int[][] distances;
    private String startCity;
    private String endCity;
    private List<Integer> shortestPath;
    private int shortestDistance;
    private Map<List<Integer>, Integer> allPathsWithDistances;

    public Question_7routeOptimization() {
        // Set up the main window of the application
        setTitle("Smart Delivery Route Optimizer");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize city positions and distances between them
        initializeCitiesAndDistances();

        // Set the layout of the main window
        setLayout(new BorderLayout(15, 15));

        // Create the graph panel to display the map and add it to the center
        EnhancedGraphPanel graphPanel = new EnhancedGraphPanel();
        graphPanel.setBackground(new Color(50, 50, 50));
        add(graphPanel, BorderLayout.CENTER);

        // Create a control panel with a vertical layout
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(30, 30, 30));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add a label for the start city selection
        JLabel startLabel = createCustomLabel("Choose Start City:");
        controlPanel.add(startLabel);

        // Add a combo box for selecting the start city
        JComboBox<String> startComboBox = createCustomComboBox(cityIndexMap.keySet().toArray(new String[0]));
        controlPanel.add(startComboBox);

        // Add a label for the end city selection
        JLabel endLabel = createCustomLabel("Choose End City:");
        controlPanel.add(endLabel);

        // Add a combo box for selecting the end city
        JComboBox<String> endComboBox = createCustomComboBox(cityIndexMap.keySet().toArray(new String[0]));
        controlPanel.add(endComboBox);

        // Create a panel for the buttons with a grid layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(new Color(30, 30, 30));

        // Create the "Find Best Route" button, make it smaller, and add an action listener
        JButton optimizeButton = createStyledButton("Find Best Route", 130, 35);
        optimizeButton.addActionListener(e -> {
            startCity = (String) startComboBox.getSelectedItem();
            endCity = (String) endComboBox.getSelectedItem();
            if (startCity != null && endCity != null && !startCity.equals(endCity)) {
                findShortestPath(startCity, endCity);
                graphPanel.repaint();
                displayPathInfo();
            } else {
                JOptionPane.showMessageDialog(this, "Please choose valid cities for start and end.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create the "Clear Path" button, make it smaller, and add an action listener
        JButton clearButton = createStyledButton("Clear Path", 130, 35);
        clearButton.addActionListener(e -> {
            shortestPath = null;
            shortestDistance = 0;
            allPathsWithDistances.clear();
            graphPanel.repaint();
            displayPathInfo();
        });

        // Add the buttons to the button panel
        buttonPanel.add(optimizeButton);
        buttonPanel.add(clearButton);

        // Add the button panel to the control panel
        controlPanel.add(buttonPanel);

        // Add the control panel to the left side of the main window
        add(controlPanel, BorderLayout.WEST);

        // Create an information panel to display path details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(30, 30, 30));
        JLabel pathInfoLabel = createCustomLabel("Path Information:");
        infoPanel.add(pathInfoLabel, BorderLayout.NORTH);

        // Create a text area for displaying path details and add it to the info panel
        JTextArea pathInfoArea = new JTextArea();
        pathInfoArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pathInfoArea.setBackground(new Color(50, 50, 50));
        pathInfoArea.setForeground(Color.WHITE);
        pathInfoArea.setEditable(false);
        infoPanel.add(new JScrollPane(pathInfoArea), BorderLayout.CENTER);

        // Add the info panel to the right side of the main window
        add(infoPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // Initialize the city positions and the distance matrix
    private void initializeCitiesAndDistances() {
        cityPositions = new HashMap<>();
        cityPositions.put("Dang", new Point(250, 150));
        cityPositions.put("Kathmandu", new Point(150, 400));
        cityPositions.put("Morang", new Point(500, 250));
        cityPositions.put("Taplejung", new Point(350, 600));
        cityPositions.put("Doti", new Point(650, 450));
        cityPositions.put("Kaski", new Point(850, 300));
        cityPositions.put("Gorkha", new Point(450, 350));

        String[] cities = {"Dang", "Kathmandu", "Morang", "Taplejung", "Doti", "Kaski", "Gorkha"};
        cityIndexMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            cityIndexMap.put(cities[i], i);
        }

        distances = new int[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0;
        }

        // Define the connections between cities and their distances
        addConnection("Dang", "Kathmandu", 400);
        addConnection("Kathmandu", "Taplejung", 600);
        addConnection("Dang", "Morang", 300);
        addConnection("Doti", "Kaski", 200);
        addConnection("Morang", "Kathmandu", 250);
        addConnection("Kathmandu", "Gorkha", 150);
        addConnection("Gorkha", "Kaski", 100);
    }

    // Add a connection between two cities in the distance matrix
    private void addConnection(String city1, String city2, int distance) {
        int index1 = cityIndexMap.get(city1);
        int index2 = cityIndexMap.get(city2);
        distances[index1][index2] = distance;
        distances[index2][index1] = distance;
    }

    // Find the shortest path between two cities using Dijkstra's algorithm
    private void findShortestPath(String startCity, String endCity) {
        int startIndex = cityIndexMap.get(startCity);
        int endIndex = cityIndexMap.get(endCity);
        shortestPath = dijkstra(startIndex, endIndex);
        shortestDistance = calculatePathDistance(shortestPath);
        allPathsWithDistances = findAllPaths(startIndex, endIndex);
    }

    // Implementation of Dijkstra's algorithm to find the shortest path
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

    // Calculate the distance of a given path
    private int calculatePathDistance(List<Integer> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += distances[path.get(i)][path.get(i + 1)];
        }
        return distance;
    }

    // Find all possible paths between two cities using recursion
    private Map<List<Integer>, Integer> findAllPaths(int start, int end) {
        Map<List<Integer>, Integer> pathsWithDistances = new HashMap<>();
        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[distances.length];

        findAllPathsHelper(start, end, currentPath, visited, pathsWithDistances);
        return pathsWithDistances;
    }

    // Helper method for findAllPaths to recursively explore all paths
    private void findAllPathsHelper(int current, int end, List<Integer> currentPath, boolean[] visited, Map<List<Integer>, Integer> pathsWithDistances) {
        visited[current] = true;
        currentPath.add(current);

        if (current == end) {
            pathsWithDistances.put(new ArrayList<>(currentPath), calculatePathDistance(currentPath));
        } else {
            for (int i = 0; i < distances.length; i++) {
                if (!visited[i] && distances[current][i] != Integer.MAX_VALUE) {
                    findAllPathsHelper(i, end, currentPath, visited, pathsWithDistances);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited[current] = false;
    }

    // Display information about the best route found
    private void displayPathInfo() {
        if (shortestPath == null) {
            JOptionPane.showMessageDialog(this, "No path found.", "Path Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder pathInfo = new StringBuilder();
        pathInfo.append("Best Route: ");
        for (int i = 0; i < shortestPath.size(); i++) {
            pathInfo.append(getCityName(shortestPath.get(i)));
            if (i < shortestPath.size() - 1) {
                pathInfo.append(" -> ");
            }
        }
        pathInfo.append("\nTotal Distance: ").append(shortestDistance).append(" km");

        JOptionPane.showMessageDialog(this, pathInfo.toString(), "Path Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Get the name of a city given its index
    private String getCityName(int index) {
        for (Map.Entry<String, Integer> entry : cityIndexMap.entrySet()) {
            if (entry.getValue().equals(index)) {
                return entry.getKey();
            }
        }
        return "";
    }

    // Create a custom styled JLabel
    private JLabel createCustomLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Create a custom styled JComboBox
    private JComboBox<String> createCustomComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, comboBox.getPreferredSize().height));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        return comboBox;
    }

    // Create a custom styled JButton
    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height)); // Set the button size
        return button;
    }

    public static void main(String[] args) {
        // Start the application by creating an instance of the main class
        SwingUtilities.invokeLater(Question_7routeOptimization::new);
    }

    // Enhanced graph panel class for drawing cities and routes
    private class EnhancedGraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Draw all cities on the graph
            for (Map.Entry<String, Point> entry : cityPositions.entrySet()) {
                String city = entry.getKey();
                Point position = entry.getValue();
                g2d.setColor(Color.WHITE);
                g2d.fillOval(position.x - 10, position.y - 10, 20, 20);
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                g2d.drawString(city, position.x + 12, position.y + 12);
            }

            // Draw all routes between cities
            g2d.setColor(Color.GRAY);
            for (int i = 0; i < distances.length; i++) {
                for (int j = i + 1; j < distances.length; j++) {
                    if (distances[i][j] != Integer.MAX_VALUE) {
                        Point p1 = cityPositions.get(getCityName(i));
                        Point p2 = cityPositions.get(getCityName(j));
                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }

            // Highlight the best route found
            if (shortestPath != null) {
                g2d.setColor(Color.YELLOW);
                for (int i = 0; i < shortestPath.size() - 1; i++) {
                    Point p1 = cityPositions.get(getCityName(shortestPath.get(i)));
                    Point p2 = cityPositions.get(getCityName(shortestPath.get(i + 1)));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }
}
