import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// --Ui---------------------------------------------------
class Ui extends JFrame {
    JButton create = new JButton("Create");
    JButton read = new JButton("Read");
    JButton update = new JButton("Update");
    JButton delete = new JButton("Delete");
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p5 = new JPanel();

    Ui() {
        setSize(700, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        setTitle("Contacts");
        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);
        add(p3, BorderLayout.NORTH);
        add(p4, BorderLayout.EAST);
        add(p5, BorderLayout.WEST);
        p4.setPreferredSize(new Dimension(200, 600));
        p5.setPreferredSize(new Dimension(200, 600));
        p1.setLayout(new GridLayout(4, 1));
        p3.setPreferredSize(new Dimension(700, 100));
        p1.setBackground(Color.DARK_GRAY);
        p2.setBackground(Color.BLUE);
        p2.setPreferredSize(new Dimension(700, 50));
        p1.add(create);
        create.setFocusable(false);
        p1.add(read);
        read.setFocusable(false);
        p1.add(update);
        update.setFocusable(false);
        p1.add(delete);
        delete.setFocusable(false);
        setResizable(false);

        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateContact();
            }
        });

        read.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ReadContacts();
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateContact();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteContact();
            }
        });

        setVisible(true);
    }

    // -- Create Contact Page -------------------------------------
    class CreateContact extends JFrame implements ActionListener {
        JButton creButton = new JButton("OK");
        JTextField nametx;
        JTextField phnotx;
        JTextField dobtx;
        JTextField tagtx;
        JTextArea addresstx;
        JTextField emailtx;

        CreateContact() {
            setSize(420, 300);
            setTitle("Create Contact");
            setLayout(new FlowLayout());
            setResizable(false);
            JLabel name = new JLabel("Name");
            nametx = new JTextField(10);
            JLabel phno = new JLabel("Phone number");
            phnotx = new JTextField(15);
            JLabel email = new JLabel("Email");
            emailtx = new JTextField(15);
            JLabel address = new JLabel("Address");
            addresstx = new JTextArea(10, 15);
            JLabel tag = new JLabel("Tag");
            tagtx = new JTextField(10);
            JLabel dob = new JLabel("DOB in --/--/---");
            dobtx = new JTextField(10);
            creButton.addActionListener(this);

            add(name);
            add(nametx);
            add(phno);
            add(phnotx);
            add(email);
            add(emailtx);
            add(address);
            add(addresstx);
            add(tag);
            add(tagtx);
            add(dob);
            add(dobtx);
            add(creButton);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == creButton) {
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "")) {
                    String query = "insert into contacts values(?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = con.prepareStatement(query)) {
                        String name = nametx.getText();
                        long phno = Long.parseLong(phnotx.getText());
                        String addres = addresstx.getText();
                        String email = emailtx.getText();
                        String tag = tagtx.getText();
                        String dateStr = dobtx.getText();
                        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        ps.setInt(1, 2); // You can update this with a proper id generation logic
                        ps.setString(2, name);
                        ps.setLong(3, phno);
                        ps.setString(4, addres);
                        ps.setString(5, email);
                        ps.setString(6, formattedDate);
                        ps.setString(7, tag);
                        int affec = ps.executeUpdate();
                        System.out.println(affec);
                    } catch (NumberFormatException | SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                creButton.setText("Contact created, close the page");
                creButton.setEnabled(false);
            }
        }
    }

    // -- Read Contacts Page -------------------------------------
    class ReadContacts extends JFrame {
        ReadContacts() {
            setSize(500, 500);
            setTitle("Read Contacts");
            setLayout(new BorderLayout());

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "")) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from contacts");

                JPanel contactsPanel = new JPanel();
                contactsPanel.setLayout(new GridLayout(0, 1));

                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    long phno = rs.getLong(3);
                    String addres = rs.getString(4);
                    String email = rs.getString(5);
                    String dateStr = rs.getString(6);
                    String tag = rs.getString(7);

                    String contactInfo = "ID: " + id + "\nName: " + name + "\nPhone Number: " + phno + "\nAddress: "
                            + addres + "\nEmail: " + email + "\nDOB: " + dateStr + "\nTag: " + tag + "\n";

                    contactsPanel.add(new JLabel(contactInfo));
                }

                JScrollPane scrollPane = new JScrollPane(contactsPanel);
                add(scrollPane, BorderLayout.CENTER);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    // -- Update Contact Page -------------------------------------
    class UpdateContact extends JFrame {
        UpdateContact() {
            setSize(420, 300);
            setTitle("Update Contact");
            setLayout(new FlowLayout());
            setResizable(false);

            JLabel idLabel = new JLabel("Enter Contact ID to update:");
            JTextField idTextField = new JTextField(10);
            JButton updateButton = new JButton("Update");

            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(idTextField.getText());
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root",
                            "")) {
                        Statement st = con.createStatement();
                        int aff = st.executeUpdate("update contacts set tag='friend' where id=" + id + ";");
                        System.out.println(aff);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    updateButton.setText("Contact updated, close the page");
                    updateButton.setEnabled(false);
                }
            });

            add(idLabel);
            add(idTextField);
            add(updateButton);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    // -- Delete Contact Page -------------------------------------
    class DeleteContact extends JFrame {
        DeleteContact() {
            setSize(420, 300);
            setTitle("Delete Contact");
            setLayout(new FlowLayout());
            setResizable(false);

            JLabel idLabel = new JLabel("Enter Contact ID to delete:");
            JTextField idTextField = new JTextField(10);
            JButton deleteButton = new JButton("Delete");

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(idTextField.getText());
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root",
                            "")) {
                        PreparedStatement ps = con.prepareStatement("delete from contacts where id =?");
                        ps.setInt(1, id);
                        int aff = ps.executeUpdate();
                        System.out.println(aff);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    deleteButton.setText("Contact deleted, close the page");
                    deleteButton.setEnabled(false);
                }
            });

            add(idLabel);
            add(idTextField);
            add(deleteButton);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}

// ################ dtata base
class Database extends Ui {
    Database() {
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateContact();
            }
        });

        read.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ReadContacts();
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateContact();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteContact();
            }
        });
    }
}

// main----------------------------------------------------------
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Database();
            }
        });
    }
}
