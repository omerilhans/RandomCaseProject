package com.omerilhanli.randomcaseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    private val listUser = ArrayList<String>()
    private val listCity = ArrayList<String>()
    private val listPair = ArrayList<String>()

    private var adapterUser = RecyclerAdapter(listUser)
    private var adapterCity = RecyclerAdapter(listCity)
    private var adapterPair = RecyclerAdapter(listPair)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerUserList.prepareIt(this, adapterUser)
        recyclerCityList.prepareIt(this, adapterCity)
        recyclerPairList.prepareIt(this, adapterPair)

        listOf(btnAddUser, btnAddCity, btnDistribute)
            .forEach { button ->
                button?.apply {
                    setOnClickListener(this@MainActivity)
                    setOnLongClickListener(this@MainActivity)
                }
            }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btnAddUser -> etUser.text.toString()
                    .let { txt ->
                        adapterUser.add(txt)
                    }

                R.id.btnAddCity -> etCity.text.toString()
                    .let { txt ->
                        adapterCity.add(txt)
                    }

                R.id.btnDistribute -> {
                    //test_with_dummy_data()
                    adapterPair.update(getPairList())
                }
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            when (it.id) {
                R.id.btnAddUser -> adapterUser.clear()
                R.id.btnAddCity -> adapterCity.clear()
                R.id.btnDistribute -> adapterPair.clear()
            }
        }
        return true
    }

    private fun getPairList(): ArrayList<String> {
        val userListSize = listUser.size
        val cityListSize = listCity.size
        if (userListSize == 0 || cityListSize == 0) {
            return listPair
        }

        val randomIndexListForUser = userListSize.getRandomIndexList()
        val randomIndexListForCity = cityListSize.getRandomIndexList()

        if (userListSize == cityListSize) {
            for (i in 0 until cityListSize) {
                val randomUser = listUser[randomIndexListForUser[i]]
                val randomCity = listCity[randomIndexListForCity[i]]
                listPair.add("$randomUser - $randomCity")
            }
        } else if (userListSize >= cityListSize) {
            val ratio = userListSize / cityListSize

            for (cityIndex in 0 until cityListSize) {
                val start = cityIndex * ratio
                val length = start + ratio
                for (userIndex in start until length) {
                    val randomUser = listUser[randomIndexListForUser[userIndex]]
                    val randomCity = listCity[randomIndexListForCity[cityIndex]]
                    listPair.add("$randomUser - $randomCity")
                }
            }

            val over = userListSize - (cityListSize * ratio)
            if (over > 0) {
                val startOver = cityListSize * ratio
                val lengthOver = userListSize
                var indexCity = 0
                for (index in startOver until lengthOver) {
                    val randomUser = listUser[randomIndexListForUser[index]]
                    if (indexCity == listCity.size) {
                        indexCity = 0
                    }
                    val randomCity = listCity[randomIndexListForCity[indexCity]]
                    indexCity++
                    listPair.add("$randomUser - $randomCity")
                }
            }
        }
        return listPair
    }

    // listeleri, dummy data ile hazırlayıp test etme ihtiyacını karşılaması için eklendi
    private fun test_with_dummy_data() {
        /*
          //single case\\
          listUser.add("user-0")
          listCity.add("city-0")
         */

        //multiple case\\
        (0..22).forEach {
            listUser.add("user-$it")
        }
        (0..3).forEach {
            listCity.add("city-$it")
        }
        adapterUser.notifyDataSetChanged()
        adapterCity.notifyDataSetChanged()
    }
}