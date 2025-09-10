package home.service.impl;

import home.Models.Category;
import home.service.CategoryService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection conn = home.util.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Category");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setCateId(rs.getInt("cateId"));
                category.setCateName(rs.getString("cateName"));
                category.setIcons(rs.getString("icons"));
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category get(int id) {
        // Implementation needed
        return null;
    }

    @Override
    public void insert(Category category) {
        // Implementation needed
    }

    @Override
    public void edit(Category category) {
        // Implementation needed
    }

    @Override
    public void delete(int id) {
        // Implementation needed
    }

	@Override
	public Category get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}