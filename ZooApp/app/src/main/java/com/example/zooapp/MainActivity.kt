package com.example.zooapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_item.view.*

class MainActivity : AppCompatActivity() {

    var listOfAnimals = ArrayList<Animal>()
    var adapter: AnimalsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfAnimals.add(Animal("Babbon","Babbon description", R.drawable.baboon, true))
        listOfAnimals.add(Animal("Panda","Panda description", R.drawable.panda, false))
        listOfAnimals.add(Animal("Swallow","Swallow description", R.drawable.swallow_bird, false))
        listOfAnimals.add(Animal("White Tiger","White Tiger description", R.drawable.white_tiger, true))
        listOfAnimals.add(Animal("Zebra","Zebra description", R.drawable.zebra, false))

        adapter = AnimalsAdapter(this,listOfAnimals)
        tvListAnimal.adapter = adapter
    }

    fun deleteAnimal(index:Int){
        listOfAnimals.removeAt(index)
        adapter!!.notifyDataSetChanged()
    }

    fun addAnimal(index:Int){
        listOfAnimals.add(listOfAnimals[index])
        adapter!!.notifyDataSetChanged()
    }

    class AnimalsAdapter: BaseAdapter{
        var listOfAnimals = ArrayList<Animal>()
        var context: Context? = null
        constructor(context: Context,
                    listOfAnimals: ArrayList<Animal>):super(){
                this.listOfAnimals = listOfAnimals
                this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val animal = listOfAnimals[position]
                if(animal.isKiller) {
                var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var myView = inflater.inflate(R.layout.animal_killer_ticket, null)
                myView.txtTitle.text = animal.name!!
                myView.txtDesc.text = animal.desc!!
                myView.ivName.setImageResource(animal.image!!)
                myView.ivName.setOnClickListener{
                    val intent = Intent(context, AnimalInfo::class.java)
                    intent.putExtra("name", animal.name)
                    intent.putExtra("desc", animal.desc)
                    intent.putExtra("image", animal.image!!)
                    context!!.startActivity(intent)
                }
                return myView
            }else{
                var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var myView = inflater.inflate(R.layout.animal_item, null)
                myView.txtTitle.text = animal.name!!
                myView.txtDesc.text = animal.desc!!
                myView.ivName.setImageResource(animal.image!!)
                    myView.ivName.setOnClickListener{
                        val intent = Intent(context, AnimalInfo::class.java)
                        intent.putExtra("name", animal.name)
                        intent.putExtra("desc", animal.desc)
                        intent.putExtra("image", animal.image!!)
                        context!!.startActivity(intent)
                    }
                return myView

            }

        }

        override fun getItem(position: Int): Any {
            return listOfAnimals[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfAnimals.size
        }

    }

}
