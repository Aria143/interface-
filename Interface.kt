// Define a data class for the chat message
data class ChatMessage(val message: String, val sender: String, val timestamp: Long)

// Create a RecyclerView adapter to display the chat messages
class ChatAdapter(private val context: Context, private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatViewHolder = holder as ChatViewHolder
        val chatMessage = messages[position]

        chatViewHolder.messageTextView.text = chatMessage.message
        chatViewHolder.senderTextView.text = chatMessage.sender

        val timeString = DateFormat.getTimeInstance().format(chatMessage.timestamp)
        chatViewHolder.timestampTextView.text = timeString
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    // Define the ViewHolder for the chat message
    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.text_message)
        val senderTextView: TextView = itemView.findViewById(R.id.text_sender)
        val timestampTextView: TextView = itemView.findViewById(R.id.text_timestamp)
    }
}

// Create the activity to display the chat messages
class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Set up the RecyclerView to display the chat messages
        chatRecyclerView = findViewById(R.id.recycler_view_chat)
        chatLayoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter(this, getChatMessages())
        chatRecyclerView.adapter = chatAdapter
        chatRecyclerView.layoutManager = chatLayoutManager

        // Set up the send message button
        val sendMessageButton: Button = findViewById(R.id.button_send)
        sendMessageButton.setOnClickListener {
            // Get the message from the text input field
            val messageInput: EditText = findViewById(R.id.edit_text_message)
            val message = messageInput.text.toString().trim()

            // Clear the message input field
            messageInput.setText("")

            // Add the new message to the list and notify the adapter of the change
            val newChatMessage = ChatMessage(message, "You", System.currentTimeMillis())
            chatAdapter.messages += newChatMessage
            chatAdapter.notifyItemInserted(chatAdapter.messages.size - 1)

            // Scroll to the bottom of the RecyclerView to show the new message
            chatRecyclerView.scrollToPosition(chatAdapter.messages.size - 1)
        }
    }

    // Returns some sample chat messages for testing purposes
private fun getChatMessages(): List<ChatMessage> {
    return listOf(
        ChatMessage("Hello", "Friend", System.currentTimeMillis() - 60000),
        ChatMessage("How are you?", "Friend", System.currentTimeMillis() - 50000),
        ChatMessage("I'm good, thanks for asking. How about you?", "You", System.currentTimeMillis() - 40000),
        ChatMessage("I'm doing pretty well too. What have you been up to lately?", "Friend", System.currentTimeMillis() - 30000),
        ChatMessage("Not much, just hanging out with friends and stuff. How about you?", "You", System.currentTimeMillis() - 20000),
        ChatMessage("Same here. Hey, do you want to grab lunch tomorrow?", "Friend", System.currentTimeMillis() - 10000)
    )
}
