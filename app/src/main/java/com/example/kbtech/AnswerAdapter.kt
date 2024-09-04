import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kbtech.R
import com.example.kbtech.Roomdb.AnswerDao
import com.example.kbtech.Roomdb.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnswerAdapter(
    private val context: Context,
    private val answers: MutableList<User>,
    private val answerDao: AnswerDao // Pass AnswerDao as a parameter
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    inner class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.Name)
        val Address: TextView = itemView.findViewById(R.id.Address)
        val Hobbies: TextView = itemView.findViewById(R.id.Hobbies)
        val Action: TextView = itemView.findViewById(R.id.Action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_data_layout, parent, false)
        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = answers[position]
        holder.Name.text = answer.name
        holder.Address.text = answer.address
        holder.Hobbies.text = answer.hobbies

        holder.Action.setOnClickListener {
            showCustomDialog(answer, position)
        }
    }

    private fun showCustomDialog(user: User, position: Int) {
        val dialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)
        dialog.setContentView(dialogView)

        val Name = dialogView.findViewById<TextView>(R.id.Name)
        val Address = dialogView.findViewById<TextView>(R.id.Address)
        val Hobbies = dialogView.findViewById<TextView>(R.id.Hobbies)
        val deleteBtn = dialogView.findViewById<TextView>(R.id.Action)

        // Populate dialog with user data
        Name.text = user.name
        Address.text = user.address
        Hobbies.text = user.hobbies

        deleteBtn.setOnClickListener {
            // Perform deletion in the background thread
            CoroutineScope(Dispatchers.IO).launch {
                // Perform database operation
                answerDao.deleteAnswer(user)
                withContext(Dispatchers.Main) {
                    answers.removeAt(position)
                    notifyItemRemoved(position)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    override fun getItemCount(): Int = answers.size
}
