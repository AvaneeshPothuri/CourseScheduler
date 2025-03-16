import javax.swing.*;
import java.awt.*;

public class SelectionWindow extends JFrame {
    public SelectionWindow() {
        setTitle("Selection Window");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headingLabel = new JLabel("Select How You Want to Create Your Timetable");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 10)); 

        JButton manualButton = new JButton("Manual");
        JButton automaticButton = new JButton("Automatic");

        manualButton.setFont(new Font("Arial", Font.BOLD, 14));
        manualButton.setBackground(new Color(70, 130, 180)); 
        manualButton.setForeground(Color.WHITE);
        manualButton.setFocusPainted(false);

        automaticButton.setFont(new Font("Arial", Font.BOLD, 14));
        automaticButton.setBackground(new Color(34, 139, 34)); 
        automaticButton.setForeground(Color.WHITE);
        automaticButton.setFocusPainted(false);

        buttonPanel.add(manualButton);
        buttonPanel.add(automaticButton);

        mainPanel.add(headingLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
