package tigerhacks.android.tigerhacksapp.maps

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import android.view.View
import com.davemorrissey.labs.subscaleview.ImageSource
import kotlinx.android.synthetic.main.fragment_map.mapView
import kotlinx.android.synthetic.main.fragment_map.tabLayout
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.shared.fragments.BaseFragment

/**
 * @author pauldg7@gmail.com (Paul Gillis)
 */
class MapFragment : BaseFragment(R.layout.fragment_map) {
    override val navId = R.id.navigation_map
    override val titleResId = R.string.title_map

    private var selection = 0
        set(value) {
            if (value == field || value < 0 || 3 < value) return
            field = value
            updateSelection()
        }

    private val tabListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            selection = tab.position
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    override fun onViewCreated(layoutView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(layoutView, savedInstanceState)
        // Inflate the layout for this fragment

        tabLayout.getTabAt(selection)?.select()
        tabLayout.addOnTabSelectedListener(tabListener)

        updateSelection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayout.removeOnTabSelectedListener(tabListener)
    }

    private fun updateSelection() {
        val asset = when (selection) {
            0 -> "floor1map.webp"
            1 -> "floor2map.webp"
            else -> "floor3map.webp"
        }
        mapView.setImage(ImageSource.asset(asset))
    }
}
