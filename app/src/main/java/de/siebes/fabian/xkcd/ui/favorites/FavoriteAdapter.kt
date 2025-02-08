package de.siebes.fabian.xkcd.ui.favorites

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.siebes.fabian.xkcd.R
import de.siebes.fabian.xkcd.helper.MyDateFormatter
import de.siebes.fabian.xkcd.model.Comic
import java.util.Locale

class FavoriteAdapter(
    private val favorites: List<Comic>
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val tvNumber: TextView = view.findViewById(R.id.tvNumber)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_favorite, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteComic = favorites[position]
        holder.tvNumber.text = String.format(Locale.GERMANY, "#%d", favoriteComic.num)
        holder.tvTitle.text = favoriteComic.title
        holder.tvDate.text = MyDateFormatter.format(favoriteComic.date)
    }
}