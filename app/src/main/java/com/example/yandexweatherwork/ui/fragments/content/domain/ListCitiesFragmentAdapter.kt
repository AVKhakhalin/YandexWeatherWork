package com.example.yandexweatherwork.ui.fragments.content.domain

import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.yandexweatherwork.R
import com.example.yandexweatherwork.domain.data.City

class ListCitiesFragmentAdapter(fragment: Fragment): RecyclerView.Adapter<ListCitiesFragmentAdapter.MainFragmentViewHolder>() {

    private var weatherData: List<City> = listOf()
    private lateinit var listener: OnItemViewClickListener

    // Переменные для контекстного меню
    private var fragment: Fragment? = null
    private var positionChoosedElement = -1

    init {
        this.fragment = fragment
    }

    //region МЕТОДЫ ДЛЯ ОБНОВЛЕНИЯ СПИСКА
    // Метод для полного обновления списка
    fun setWeather(data: List<City>){
        weatherData = data
        notifyDataSetChanged()
    }
    // Метод для обновления списка после удаления его элемента
    fun setWeather(data: List<City>, updatedPosition: Int){
        weatherData = data
        notifyItemRemoved(updatedPosition)
    }
    // Метод для обновления списка после редактирования его элемента
    fun setWeather(updatedPosition: Int, data: List<City>){
        weatherData = data
        notifyItemChanged(updatedPosition)
    }
    //endregion

    fun setOnItemViewClickListener(onItemViewClickListener:OnItemViewClickListener) {
        listener = onItemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        return MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_cities_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.render(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class MainFragmentViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        init {
            // Регистрация стартового элемента для контекстного меню
            fragment?.registerForContextMenu(view.findViewById(R.id.recycler_item_text_view))
            // Регистрация метода onCreateContextMenu
            view.setOnCreateContextMenuListener(this)
        }

        fun render(city: City){
            with(itemView) {
                findViewById<TextView>(R.id.recycler_item_text_view).text = city.name
                setOnClickListener{
                    listener.onItemClick(city)
                }
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
//            Toast.makeText(v?.context, "${getLayoutPosition()} ${weatherData.get(getLayoutPosition()).name}", Toast.LENGTH_SHORT).show()
            setPositionChoosedElement(layoutPosition)
        }
    }

    // Получение позиции выбранного элемента в списке
    fun getPositionChoosedElement(): Int {
        return positionChoosedElement
    }

    // Установка позиции выбранного элемента в списке
    fun setPositionChoosedElement(position: Int) {
        this.positionChoosedElement = position
    }
}