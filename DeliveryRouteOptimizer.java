import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DeliveryRouteOptimizer extends JFrame {
    private JTextArea deliveryListArea;
    private JTextField vehicleCapacityField;
    private JButton optimizeButton;
    private JTextArea resultArea;

    private Map<String, Map<String, Integer>> graph;

    public DeliveryRouteOptimizer() {
        setTitle("Delivery Route Optimizer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        initGraph();

        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Delivery List (one per line):"));
        deliveryListArea = new JTextArea(5, 20);
        inputPanel.add(new JScrollPane(deliveryListArea));

        inputPanel.add(new JLabel("Vehicle Capacity:"));
        vehicleCapacityField = new JTextField("10");
        inputPanel.add(vehicleCapacityField);

        optimizeButton = new JButton("Optimize Route");
        optimizeButton.addActionListener(e -> optimizeRoute());
        inputPanel.add(optimizeButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void initGraph() {
        graph = new HashMap<>();

        // Example graph initialization with some predefined routes and distances
        addEdge("A", "B", 4);
        addEdge("A", "C", 2);
        addEdge("B", "C", 1);
        addEdge("B", "D", 5);
        addEdge("C", "D", 8);
        addEdge("C", "E", 10);
        addEdge("D", "E", 2);
        addEdge("D", "F", 6);
        addEdge("E", "F", 3);
    }

    private void addEdge(String from, String to, int distance) {
        graph.putIfAbsent(from, new HashMap<>());
        graph.putIfAbsent(to, new HashMap<>());
        graph.get(from).put(to, distance);
        graph.get(to).put(from, distance); // assuming bidirectional routes
    }

    private void optimizeRoute() {
        String start = "A"; // For example, starting point is "A"
        Map<String, Integer> distances = dijkstra(start);

        StringBuilder result = new StringBuilder();
        for (String location : distances.keySet()) {
            result.append(location).append(": ").append(distances.get(location)).append(" units\n");
        }

        resultArea.setText(result.toString());
    }

    private Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String location : graph.keySet()) {
            distances.put(location, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visited.add(current);

            for (Map.Entry<String, Integer> neighbor : graph.get(current).entrySet()) {
                if (visited.contains(neighbor.getKey())) continue;

                int newDist = distances.get(current) + neighbor.getValue();
                if (newDist < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDist);
                    queue.add(neighbor.getKey());
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeliveryRouteOptimizer::new);
    }
}
